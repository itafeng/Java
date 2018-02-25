package com.itnovice.concurrency;

public class ThreadDemo1 {
    public static void main(String[] args) {

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        task.run();  // Run in current thread

        Thread t = new Thread(task);
        t.start();   // Run in new thread

        System.out.println("Done");
    }
}
