package com.xyvo.defi.standalone;

import com.xyvo.defi.DefiTrackerPortfolioApplication;
import com.xyvo.defi.domain.transactions.Pending;
import com.xyvo.defi.repository.api.PendingRepo;
import com.xyvo.defi.server.AsyncTaskManager;
import com.xyvo.defi.server.TaskManager;
import com.xyvo.defi.utils.Utils;
import com.xyvo.defi.server.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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
        return "Shutdown hooks are called.";
    }

    private Runnable asyncShutdown() {
        return () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                //ignore
            } finally {
                Utils.CONSOLE_LOG.warn("Shutting Down System.");
                System.exit(SpringApplication.exit(DefiTrackerPortfolioApplication.getApplicationContext()));
            }
        };
    }

}
