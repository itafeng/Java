package Concurrency.src.com.itnovice.concurrency;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadDemo20 {
    public static void main(String[] args) {
        ScheduledExecutorService threadpool = Executors.newScheduledThreadPool(1);

        threadpool.scheduleAtFixedRate(() -> {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            System.out.println(String.format("[%s] - Triggered", dateFormat.format(cal.getTime())));

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ie) {

            }
        }, 0, 1, TimeUnit.SECONDS);

        ScheduledFuture<?> secondThread = threadpool.scheduleWithFixedDelay(() -> {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            System.out.println(String.format("Triggered - [%s]", dateFormat.format(cal.getTime())));

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ie) {

            }
        }, 0, 1, TimeUnit.SECONDS);

        try {
            System.out.println("Cancelling second thread");
            TimeUnit.SECONDS.sleep(10);
            secondThread.cancel(true);
            System.out.println("Second thread cancelled");
        } catch (InterruptedException ie) {

        }

    }
}
