package Concurrency.src.com.itnovice.concurrency;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadDemo11 {
    private static boolean isLeader;

    static ConcurrentMap<String, String> sharedMemory = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        testRun();
    }

    public static void testRun() {
        ExecutorService leaderElectionWorker = Executors.newSingleThreadExecutor();
        leaderElectionWorker.submit(() -> {
            ScheduledExecutorService acquireLock = Executors.newSingleThreadScheduledExecutor();
            while (true) {
                Future<Long> lockAcquired = acquireLock.schedule(() -> sum(), 2, TimeUnit.SECONDS);

                try {
                    Long result = lockAcquired.get();
                    long curTime = System.currentTimeMillis();
                    Date date = new Date(curTime);
                    String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(date.getTime());

                    String key = "[Thread01-" + timeStamp + "]";
                    sharedMemory.put(key, "Thread01");
                    System.out.println("Thread01- Adding key-value to shared Memory ");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        ScheduledExecutorService secondThread = Executors.newSingleThreadScheduledExecutor();
        secondThread.scheduleWithFixedDelay(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Thread02-Item in SharedMemory: " + sharedMemory.size());
            } catch (InterruptedException e) {
                System.err.println("Task interrupted");
            }
        }, 0, 2, TimeUnit.SECONDS);

        System.out.println("Exiting main thread");

    }

    public synchronized static long sum() {
        long[] sum = {0};
        IntStream.range(0, 100).forEach(i -> {
            sum[0] += i;
        });

        return sum[0];
    }
}
