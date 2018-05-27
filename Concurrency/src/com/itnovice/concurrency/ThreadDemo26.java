package Concurrency.src.com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import sun.rmi.runtime.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadDemo26 {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                ConcurrentUtils.Log("An exception happened" + t);
            }
        }).build());

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    ConcurrentUtils.Log("Hit me!");
                    ConcurrentUtils.Log("Throwing runtime exception");
                    throw new RuntimeException();
                } catch (Throwable t) {
                    ConcurrentUtils.Log("Caught throwable t");
                    throw t;
                }
            }
        }, 0, 3, TimeUnit.SECONDS);

    }



}
