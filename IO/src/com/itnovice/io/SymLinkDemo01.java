package com.itnovice.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SymLinkDemo01 {
    private static final String PATH = "/Users/fengtang/test";
    private static long ver = 0;

    public static void main(String[] args) {
        runTest();
    }

    public static void runTest() {
        ScheduledExecutorService thread = Executors.newSingleThreadScheduledExecutor();

        thread.scheduleAtFixedRate(() -> {
            ++ver;
            String filePathStr = String.format("/Users/fengtang/test/%s", String.format("filename_%d", ver % 10));

            File file = new File(filePathStr);
            Path target = Paths.get(filePathStr);
            Path symLink = Paths.get("/Users/fengtang/test/symlink");

            try {
                FileOutputStream fos = new FileOutputStream(file, false);
                fos.write(Long.toString(ver).getBytes());
                fos.close();
                Files.createSymbolicLink(symLink, target);
            } catch (IOException ioe) {

            } catch (UnsupportedOperationException x) {

            }

            System.out.println("Current time = " + System.currentTimeMillis());

        }, 0, 2, TimeUnit.SECONDS);


        Map<String, Double> rates = new HashMap<>();
        rates.put("FOO||HTTP||HTTP_TCP", 0.25d);
        rates.put("FOO||DNS||DNS_UDP", 0.28d);
        rates.put("FOO||HTTPS||SYN_TCP", 0.28d);

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            sb.append(String.format("%s, %f, %d", entry.getKey(), entry.getValue(), System.currentTimeMillis())).append(System.getProperty("line.separator"));
        }
        System.out.println(sb.toString());

    }

}
