package Concurrency.src.com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;



public class ThreadDemo18 {

    public static void main(String[] args) {
        runTest();

        System.out.println("Exiting main()");
    }

    private static void runTest() {

//      ExecutorService innerES = Executors.newSingleThreadExecutor();
        ExecutorService innerES = Executors.newFixedThreadPool(1);

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
