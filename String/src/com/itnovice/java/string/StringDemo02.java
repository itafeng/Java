package com.itnovice.java.string;

import com.sun.tools.corba.se.idl.StringGen;

public class StringDemo02 {
    public static final String TOTAL_AVERAGE_RATE_PER_SECOND = "Total_Average_Rate_Per_Second";
    public static final String TOTAL_AVERAGE_RATE_PER_SECOND_SUFFIX = "_Average_Rate_Per_Second";
    public static final String TOTAL_AVERAGE_RATE_PER_SECOND_PREFIX = "Total_";

    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        String string = "Total_UDP_DNS_Average_Rate_Per_Second";

        String metric = "default";

        if (string.endsWith(TOTAL_AVERAGE_RATE_PER_SECOND_SUFFIX)) {
            if (!string.equalsIgnoreCase(TOTAL_AVERAGE_RATE_PER_SECOND)) {
                metric = string.substring(TOTAL_AVERAGE_RATE_PER_SECOND_PREFIX.length(),
                        string.indexOf(TOTAL_AVERAGE_RATE_PER_SECOND_SUFFIX));
            }
        }

        System.out.println(metric);
    }

}
