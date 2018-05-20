package Concurrency.src.com.itnovice.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import TcpSocketCommProto.src.TCPUtils;
import org.w3c.dom.css.Counter;

public class ThreadDemo21 {

//    private static volatile int counter = 0;

    private static class metadata {
        int counter;
    }

    public static void main(String[] args) {
        ScheduledExecutorService task = Executors.newScheduledThreadPool(10);
        AtomicInteger counter = new AtomicInteger(0);

        int i = 0;

        while (i < 10) {

            sleep(5);

            task.scheduleAtFixedRate(() -> {
                String threadName = Thread.currentThread().getName();
                int newCounter = counter.get();
                System.out.println(String.format("[%s] counter = %d" , threadName, newCounter));
            }, 0, 2, TimeUnit.SECONDS);

            counter.getAndIncrement();
            ++i;
        }

    }

    private static void sleep(int sleepTime) {
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException ie) {

        }
    }

}
