package com.itnovice.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileDemo02 {
    public static void main(String[] args) {
        runTest();
    }

    private static int ver = 0;


    private static void runTest() {
        ScheduledExecutorService thread = Executors.newSingleThreadScheduledExecutor();
        String s = "Hello World! ";

        thread.scheduleAtFixedRate(() -> {
            ++ver;
            String filePathStr = String.format("/Users/fengtang/test/%s", String.format("filename_%d", ver % 10));

            File file = new File(filePathStr);
            try {
                FileOutputStream fos = new FileOutputStream(file, false);
                fos.write((s + ver).getBytes());
                fos.close();
            } catch (IOException ioe) {

            }

        }, 0, 2, TimeUnit.SECONDS);
    }
}
