package com.xyvo.defi.standalone;

import com.xyvo.defi.utils.AsyncTaskManager;
import com.xyvo.defi.utils.TaskManager;
import com.xyvo.defi.utils.Utils;
import com.xyvo.defi.server.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(method = {RequestMethod.POST})
//@CrossOrigin(origins = Utils.ORIGIN)
//@CrossOrigin()
public class ShutdownController {

    private TaskManager taskManager;

    @Autowired
    public ShutdownController(AsyncTaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @PostMapping(path = "/standalone/shutdown")
    public String shutDown(@RequestBody ShutdownSignal payLoad) {
        if(!payLoad.getSignal().equals("shutdown")) {
            throw new ServerException("Invalid message.");
        }

        ImportExportRunner.getInstance().runExportScript();
        taskManager.execute(asyncShutdown());
        taskManager.shutdown();
        return "Shutdown successfully";
    }

    private Runnable asyncShutdown() {
        return () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //ignore
            } finally {
                Utils.CONSOLE_LOG.warn("Shutting Down System.");
                System.exit(0);
            }
        };
    }

}
