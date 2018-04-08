package com.itnovice.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileDemo01 {
    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        String s = "Hello World2! ";
        byte data[] = s.getBytes();
        Path p = Paths.get("/Users/fengtang/test/testfile");

        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)))  {
            out.write(data, 0, data.length);
        } catch (IOException ie) {
            System.err.println(ie);
        }
    }
}
