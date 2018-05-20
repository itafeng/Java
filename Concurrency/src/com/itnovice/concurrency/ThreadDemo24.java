package Concurrency.src.com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

import TcpSocketCommProto.src.TCPUtils;

public class ThreadDemo24 {

    protected static class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable ex) {
            System.out.println(String.format("Thread %s exited with an uncaught exception: %s", t.getName(), ex));
        }
    }

    public static void main(String[] args) {

        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        final ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        executorService.submit(() -> {
            for (int i = 0; i < 100; ++i) {
                try {
                    final Future<String> myValue = completionService.take();
                    final String result = myValue.get();
                    System.out.println(result);
                } catch (InterruptedException ie) {
                    return;
                } catch (ExecutionException ee) {
                System.err.println("Task failed");
                }
            }
        });


        for (int i = 0; i < 100; ++i) {
            completionService.submit(() -> {
                if (Math.random() > 0.5) {
                    throw new RuntimeException("Failed");
                }
                return "Success";
            });
        }

        executorService.shutdownNow();

        System.out.println("Exiting main()");
    }

}
