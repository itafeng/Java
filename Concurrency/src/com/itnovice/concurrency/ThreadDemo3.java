package Concurrency.src.com.itnovice.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadDemo3 {
    public static void main(String[] args) {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.submit(() -> {
            System.out.println("Hello " + Thread.currentThread().getName());
        });

        // Shutdown executor
        try {
            System.out.println("Attempt to shutdown executor");
            ex.shutdown();
            ex.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!ex.isTerminated()) {
                System.out.println("Unfinished task has been cancelled");
            }
            ex.shutdownNow();
            System.out.println("Executor shutdown completed");
        }
    }
}
