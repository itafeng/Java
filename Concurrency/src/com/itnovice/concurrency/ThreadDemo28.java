package Concurrency.src.com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.lang.Thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 *   UncaughtExcetpionHandler can only catch exception thrown from task submitted by the execute() method.
 */


public class ThreadDemo28 extends Thread {

    private static Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread th, Throwable ex) {
            System.out.println("Thread uncaught exception is caught in here !!! " + th + " Throwable: " + ex);
        }
    };

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                .setNameFormat("MyThread")
                .setUncaughtExceptionHandler(handler)
                .build());

        exec.execute(() -> {
            while (true) {
                ConcurrentUtils.Log("running thread ...");

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {

                }

                throw new RuntimeException("Runtime exception !");
            }
        });


        System.out.println("Exit from main()");
    }

}
