package Concurrency.src.com.itnovice.concurrency;

import java.util.concurrent.*;
import java.util.*;

public class ThreadPoolExecutorHandler {

    private static class MyThreadPoolExecutor extends ThreadPoolExecutor {
        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                    TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        public void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            // If submit() method is called instead of execute()
            if (t == null && r instanceof Future<?>) {
                try {
                    Object result = ((Future<?>) r).get();
                } catch (CancellationException e) {
                    t = e;
                } catch (ExecutionException e) {
                    t = e.getCause();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (t != null) {
                // Exception occurred
                System.err.println("Uncaught exception is detected! " + t
                        + " st: " + Arrays.toString(t.getStackTrace()));
                // ... Handle the exception
                // Restart the runnable again
                execute(r);
            }
            // ... Perform cleanup actions
        }
    }

    final static class MyTask implements Runnable {
        @Override public void run() {
            System.out.println("My task is started running...");
            // ...
            throw new ArithmeticException(); // uncatched exception
            // ...
        }
    }

    public static void main(String[] args) {
        // Create a fixed thread pool executor
        ExecutorService threadPool = new MyThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        threadPool.execute(new MyTask());

    }
}






