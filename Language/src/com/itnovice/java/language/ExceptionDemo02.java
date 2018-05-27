package com.itnovice.java.language;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExceptionDemo02 {
    public static void main(String[] args) {
        try {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    int i = 1;
                    int j = 0;

                    int x = i / j;
                } /*catch (RuntimeException re) {
                    System.out.println("Exception caught by inner catch statement: " + re);
                    throw re;
                } */catch (Throwable t) {
                    System.out.println("Caught throwable");
                    throw t;
                }
            }, 0, 2, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println("Exception caught by outter catch statement: " + ex);
        }
    }
}
