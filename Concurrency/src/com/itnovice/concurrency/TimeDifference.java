package Concurrency.src.com.itnovice.concurrency;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TimeDifference {
    private static final int TIMEOUT = 30;

    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        long oldTime = 1525756609115L;
        long duration = MILLISECONDS.convert(TIMEOUT, SECONDS);
        System.out.println("Current time = " + oldTime);


        System.out.println(System.currentTimeMillis() - oldTime > duration);
    }
}
