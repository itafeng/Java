package com.itnovice.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadDemo6 {
    public static void main(String[] args) {
        ScheduledExecutorService ex = Executors.newScheduledThreadPool(1);

        // one-shot execution
        System.out.println("Schedule a one-shot execution ... ");
        ScheduledFuture<?> future = ex.schedule(() -> {
           try {
               System.out.println("Scheduling: " + System.nanoTime());
           } catch (Exception e) {
               e.printStackTrace();
           }
        }, 3, TimeUnit.SECONDS);

        try {
            TimeUnit.MILLISECONDS.sleep(1337);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);

        System.out.printf("Remaining Delay: %sms\n", remainingDelay);

        System.out.println("Schedule a periodical execution ... ");
        ex.scheduleWithFixedDelay(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                System.err.println("Task interrupted");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
