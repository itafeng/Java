package com.itnovice.concurrency;

public class ThreadDemo {
    public static void main(String[] args) {
        Thread task = new Thread(new Task());
        task.start();

        System.out.println("Done");
    }

    private static class Task implements Runnable {
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        }
    }
}
