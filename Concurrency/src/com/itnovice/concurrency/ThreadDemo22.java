package Concurrency.src.com.itnovice.concurrency;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadDemo22 {
    private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        ScheduledExecutorService task = Executors.newScheduledThreadPool(5);
        AtomicInteger counter = new AtomicInteger(0);

        ConcurrentMap<Integer, ScheduledFuture<?>> hashTable = new ConcurrentHashMap<>();

        int i = 0;

        while (i < 10) {

            sleep(5);

            int rand = (new Random()).nextInt(20);

            ScheduledFuture<?> ret = task.scheduleAtFixedRate(() -> {
                String threadName = Thread.currentThread().getName();
                int newCounter = counter.get();
                System.out.println(String.format("[%s] counter = %d" , threadName, newCounter));
            }, 0, 2, TimeUnit.SECONDS);

            hashTable.put(counter.get(), ret);

            counter.getAndIncrement();
            ++i;

            hashTable.forEach((k, v) -> System.out.println("key: " + k + " value: " + v));

        }

    }

    private static void sleep(int sleepTime) {
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException ie) {

        }
    }

}
