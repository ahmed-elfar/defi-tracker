package com.xyvo.defi.server;

import com.xyvo.defi.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AsyncTaskManager implements TaskManager {

    private final ExecutorService executorService;

    public AsyncTaskManager() {
        int threads = Math.max(1 , Runtime.getRuntime().availableProcessors() - 1);
        Utils.CONSOLE_LOG.info("Initializing ThreadPool with {} threads.", threads);
        executorService = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }
}
