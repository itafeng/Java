package com.itnovice.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadDemo12 {
    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

        worker.scheduleAtFixedRate(() -> {
            System.out.println("Before return  ...");
            return;
        }, 0, 2, TimeUnit.SECONDS);
    }
}
