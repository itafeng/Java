package com.itnovice.io;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.*;
import java.nio.file.*;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FileDemo03 {
    public static void main(String[] args) {
        runTest();
    }

    private static int ver = 0;


    private static void runTest() {
        ScheduledExecutorService thread = Executors.newSingleThreadScheduledExecutor();

        final ScheduledFuture<?> scheduledFuture = thread.scheduleAtFixedRate(() -> {
            ++ver;
            String srcPathStr = String.format("/Users/fengtang/test/tmp/%s", String.format("filename_%d", ver % 10));
            String destPathStr = "/Users/fengtang/test/tmp/filename";

            String s = Long.toString(System.currentTimeMillis());
            System.out.println("Current Time: " + s);

            Path source = Paths.get(srcPathStr);
            Path destination = Paths.get(destPathStr);

            try {
                byte[] bytes = (s + ver + "\n").getBytes();
                System.out.println("Writing to " + srcPathStr );
                Files.write(source, bytes);
//                System.out.println("Moving to " + destPathStr);
//                Files.move(source, destination, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException ioe) {
                System.out.println("IOException: " + ioe.getMessage());
                ioe.printStackTrace();
            }

        }, 0, 5, TimeUnit.SECONDS);
    }
}
