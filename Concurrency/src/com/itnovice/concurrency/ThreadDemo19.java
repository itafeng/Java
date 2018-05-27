package Concurrency.src.com.itnovice.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import TcpSocketCommProto.src.TCPUtils;

public class ThreadDemo19 {

    protected static class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable ex) {
            System.out.println(String.format("Thread %s exited with an uncaught exception: %s", t.getName(), ex));
        }
    }

    public static void main(String[] args) {

        ExecutorService outterES = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                            .setDaemon(true)
                            .setUncaughtExceptionHandler(new DefaultExceptionHandler())
                            .build());

        outterES.execute(() -> {
            while (true) {

                try {
                    TCPUtils.Log("Message from thread_pool_1");
                    Thread.sleep(5000);

                } catch (InterruptedException ie) {}

            }
        });

        System.out.println("Exiting main()");
    }

}
