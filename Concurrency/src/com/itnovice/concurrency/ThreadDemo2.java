package Concurrency.src.com.itnovice.concurrency;

import java.lang.InterruptedException;
import java.util.concurrent.TimeUnit;

public class ThreadDemo2 {
    public static void main(String[] args) {

        new Thread(() -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Foo " + name);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("Done");
    }
}
