package Concurrency.src.com.itnovice.concurrency;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProactiveHandler {

    final static class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("MyTask starts running ...");
                helper();
            } catch (Throwable t) {
                System.out.println("Uncaught exception detected! " + t + " st: " + Arrays.toString(t.getStackTrace()));
            }
        }

        private void helper() {
            throw new ArithmeticException();
        }
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        threadPool.execute(new MyTask());
    }

}
