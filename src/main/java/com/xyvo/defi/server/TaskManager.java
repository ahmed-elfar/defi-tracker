package com.xyvo.defi.server;

import java.util.concurrent.ExecutorService;

public interface TaskManager {

    /**
     * Check {@link ExecutorService#execute(Runnable)}
     */
    void execute(Runnable runnable);

    /**
     * Check {@link ExecutorService#shutdown()}
     */
    void shutdown();
}
