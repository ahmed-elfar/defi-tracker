package com.xyvo.defi.server;

public interface TaskManager {

    void execute(Runnable runnable);

    void shutdown();
}
