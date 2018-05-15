package com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ThreadDemo13 {
    private static class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable ex) {
            System.out.println(String.format("Thread %s exited with an uncaught exception! %s", t.getName(), ex));
        }
    }


    public static void main(String[] args) {
        try {
            runTest();
        } catch (Exception ex) {
            System.out.println("Caught exception in main()" + ex);
        } catch (Throwable ex) {
            System.out.println("Caught throwable in main() " + ex);
        }

        System.out.println("Exiting main() function");
    }

    private static void runTest() {
        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        ExecutorService receiver = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("Receiver Thread")
                .setUncaughtExceptionHandler(new DefaultExceptionHandler())
                .build());

        ScheduledFuture timer =  worker.scheduleAtFixedRate(() -> {
            System.out.println("Before return  ...");
            return;
        }, 0, 2, TimeUnit.SECONDS);

        try {

            receiver.execute(() -> {
                while (true) {
                    System.out.println("Receiver here ...");

                    int res;
                    for (int i = 6; i > 0; --i) {
                        System.out.println("i = " + i);
                        try {
                            TimeUnit.MILLISECONDS.sleep(1500);
                            res = 8 / (i - 3);
                        } catch (InterruptedException ie) {

                        }
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println("Caught exception in runTest() " + ex);
        } catch (Throwable ex) {
            System.out.println("Caught throwable in runTest() " + ex);
        }


//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException ie) {
//            System.out.println("Sleep interrupted");
//        }

//        System.out.println("Cancelling timer object");
//        timer.cancel(true);
//        System.out.println("Timer object cancelled");
    }
}
