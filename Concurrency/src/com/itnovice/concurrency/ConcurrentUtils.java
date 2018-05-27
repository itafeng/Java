package Concurrency.src.com.itnovice.concurrency;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ConcurrentUtils {
    public static void stop(ExecutorService ex) {
        try {
            ex.shutdown();
            ex.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Termination Interrupted");
        } finally {
            if (!ex.isTerminated()) {
                System.err.println("Killing running tasks");
            }
            ex.shutdownNow();
        }
    }

    public static void Log(String message) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println(String.format("[%s] %s", simpleDateFormat.format(calendar.getTime()), message));
    }
}
