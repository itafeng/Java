package com.itnovice.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ThreadDemo8 {
    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 0;
    private static void increment() {
        lock.lock();
        try {
            ++count;
        } finally {
            lock.unlock();
        }
    }

    private static void runTest() {
        ExecutorService ex = Executors.newFixedThreadPool(2);
        try {
            IntStream.range(0, 10000)
                    .forEach(i -> ex.submit(ThreadDemo8::increment));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConcurrentUtils.stop(ex);

        System.out.println("Count = " + count);
    }

    public static void main(String[] args) {
        runTest();
    }
}
