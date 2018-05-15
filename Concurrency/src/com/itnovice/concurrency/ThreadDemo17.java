package Concurrency.src.com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadDemo17 {

    public static void main(String[] args) {
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

}
