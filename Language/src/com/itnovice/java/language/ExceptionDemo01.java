package com.itnovice.java.language;

public class ExceptionDemo01 {
    public static void main(String[] args) {
        runTest();
        System.out.println("Exiting main()");
    }

    private static void runTest() {
        int i = 0;
//        try {
            while (true) {
                //try {
                    if (i % 10 == 0) {
                        throw new RuntimeException("i is too big");
                    }
                //}
//                } catch (RuntimeException re) {
//                    System.out.println("Caught RuntimeException ... i = " + i);
//                }
                ++i;
            }
//        } catch (Exception ex) {
//
//        } finally {
//            System.out.println("Entering finally");
//        }

        //System.out.println("Exiting runTest()");
    }
}
