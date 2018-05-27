package Concurrency.src.com.itnovice.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ThreadDemo7 {
    static int count = 0;
    static synchronized void increment() {
        ++count;
        System.out.println("Thread Name: " + Thread.currentThread().getName() + " Time: " + System.nanoTime());
    }

    private static void runTest() {
        ExecutorService ex = Executors.newFixedThreadPool(5);

        IntStream.range(0, 10000)
                .forEach(i -> ex.submit(ThreadDemo7::increment));

        ConcurrentUtils.stop(ex);

        System.out.println("count = " + count);
    }

    public static void main(String[] args) {
        runTest();
    }
}
