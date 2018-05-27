package Concurrency.src.com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class ThreadDemo25 {

    public static void main(String[] args) {
        runTest();

        System.out.println("Exiting main()");
    }

    private static void runTest() {
        runTest1();
    }

    private static void runTest1() {
        runTest2();
    }
    private static void runTest2() {
        runTest3();
    }
    private static void runTest3() {
        runTest4();
    }
    private static void runTest4() {
        runTest5();
    }

    private static void runTest5() {

        ExecutorService innerES = Executors.newSingleThreadExecutor();

        innerES.submit(() -> {
            while (true) {
                try {
                    //System.out.println("Message from thread_pool_2");
                    ConcurrentUtils.Log("Message from thread");
                    Thread.sleep(2000);

                } catch (InterruptedException ie) {}
            }
        });

    }
}
