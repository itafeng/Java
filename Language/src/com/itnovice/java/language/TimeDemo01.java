package com.itnovice.java.language;

public class TimeDemo01 {
    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        Long curTime = System.currentTimeMillis();
        System.out.println(curTime);
    }
}
