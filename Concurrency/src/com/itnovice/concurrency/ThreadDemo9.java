package com.itnovice.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo9 {
    private static void runTest() {
        ExecutorService ex = Executors.newFixedThreadPool(3);
        ReentrantLock lock = new ReentrantLock();

        ex.submit(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Task interrupted");
            }finally {
               lock.unlock();
           }
        });

        ex.submit(() -> {
            System.out.println("Thread Name: " + Thread.currentThread().getName() + " Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            System.out.println("Lock acquired: " + lock.tryLock());
        });
    }

    public static void main(String[] args) {
        runTest();
    }
}
