package com.xyvo.defi.utils;

public interface TaskManager {

    void execute(Runnable runnable);

    void shutdown();
}
