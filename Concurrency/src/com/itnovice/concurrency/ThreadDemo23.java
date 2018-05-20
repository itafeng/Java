package Concurrency.src.com.itnovice.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadDemo23 {

    public static void main(String[] args) {
        ScheduledExecutorService task = Executors.newScheduledThreadPool(10);
        AtomicInteger counter = new AtomicInteger(0);
        ThreadLocal<Integer> id = ThreadLocal.withInitial(counter::incrementAndGet);
        List<String> hosts = Arrays.asList("BW01", "BW02", "BW03", "BW04", "BW05", "BW06", "BW07", "BW08", "BW09", "BW10");
        ThreadLocal<String> localHost = new ThreadLocal<>();


        int i = 0;
        while (i < 10) {
            // sleep 5 seconds
            sleep(5);
            task.scheduleAtFixedRate(() -> {
                localHost.set(hosts.get(id.get()));
                String threadName = Thread.currentThread().getName();
                System.out.println(String.format("[%s - %s] counter = %d" , localHost.get(), threadName, id.get()));
            }, 0, 2, TimeUnit.SECONDS);

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
