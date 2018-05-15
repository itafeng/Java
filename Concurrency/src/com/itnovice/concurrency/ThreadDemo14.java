package Concurrency.src.com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadDemo14 {

    protected static class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable ex) {
            System.out.println(String.format("Thread %s exited with an uncaught exception: %s", t.getName(), ex));
        }
    }

    public static void main(String[] args) {
        runTest();

        ExecutorService outterES = Executors.newSingleThreadExecutor();
        outterES.execute(() -> {
            while (true) {

                try {
                    System.out.println("Message from thread_pool_1");
                    Thread.sleep(2000);

                } catch (InterruptedException ie) {}

            }
        });

        System.out.println("Exiting main()");
    }

        private static void runTest() {
            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

            ses.scheduleAtFixedRate(() -> {
                System.out.println("Message from timer thread");
            }, 0, 2, TimeUnit.SECONDS);

            ExecutorService innerES = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .setNameFormat("my-thread-pool")
                    .setUncaughtExceptionHandler(new DefaultExceptionHandler())
                    .build());


//          ExecutorService innerES = Executors.newFixedThreadPool(1);

            innerES.execute(() -> {
                while (true) {
                    try {
                        System.out.println("Message from thread_pool_2");
                        Thread.sleep(2000);

                    } catch (InterruptedException ie) {}
                }
            });

        }
}
