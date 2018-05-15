package Concurrency.src.com.itnovice.concurrency;

import java.lang.Thread;

public class ThreadDemo15 extends Thread {

    private static Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread th, Throwable ex) {
            System.out.println("Thread uncaught exception is caught in here !!! " + th + " Throwable: " + ex);
        }
    };

    public static void main(String[] args) throws InterruptedException {

        ThreadDemo15 t = new ThreadDemo15();

        try {
            t.setUncaughtExceptionHandler(handler);
            t.start();
            t.join();
        } catch (RuntimeException e) {
            System.out.println("** RuntimeException from main method");
        }

        System.out.println("Exit from main()");
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("** Starting thread function");

                sleep(2000);

                throw new RuntimeException("Exception from thread");
            }
        } catch (RuntimeException re) {
            System.out.println("** RuntimeException from thread function caught. Rethrowing ...");
            throw re;
        } catch (InterruptedException ie) {

        }
    }


}
