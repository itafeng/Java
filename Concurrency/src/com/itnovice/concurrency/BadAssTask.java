package Concurrency.src.com.itnovice.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  Demos from http://code.nomad-labs.com/2011/12/09/mother-fk-the-scheduledexecutorservice/
 */

public class BadAssTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Sleeping ...");

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.out.println("Throwing ...");
        throw new RuntimeException("bad ass!");
    }

    public static void main(String[] args) {
        try {
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new BadAssTask(), 1, 1, TimeUnit.SECONDS);
        } catch (RuntimeException re) {
            System.out.println("Caught RuntimeException: " + re);
        } catch (Throwable tr) {
            System.out.println("Caught exception: " + tr);
        }
    }
}
