package com.itnovice.concurrency;

import java.util.concurrent.*;

public class ThreadDemo4 {
    public static void main(String[] args) throws ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });


        try {
            Integer result = future.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("Timeout getting future");
        } catch (InterruptedException e) {
            System.out.println("Result is not available");
        } finally {
            System.out.println("Done");
        }
    }
}
