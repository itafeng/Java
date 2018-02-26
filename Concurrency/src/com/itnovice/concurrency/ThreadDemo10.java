package com.itnovice.concurrency;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadDemo10 {
    private static boolean isLeader;
    public static void main(String[] args) {
        testRun();
    }

    public static void testRun() {
        ExecutorService leaderElectionWorker = Executors.newSingleThreadExecutor();
        leaderElectionWorker.submit(() -> {
            ScheduledExecutorService acquireLock = Executors.newSingleThreadScheduledExecutor();
            while (true) {
                Future<Long> lockAcquired = acquireLock.schedule(() -> sum(), 3, TimeUnit.SECONDS);

                try {
                    Long result = lockAcquired.get();
                    long curTime = System.currentTimeMillis();
                    Date date = new Date(curTime);
                    String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(date.getTime());

                    System.out.println("[" + timeStamp + "] - result = " + result);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public synchronized static long sum() {
        long[] sum = {0};
        IntStream.range(0, 100).forEach(i -> {
            sum[0] += i;
        });

        return sum[0];
    }
}
