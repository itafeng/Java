package com.itnovice.java.networking;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkingDemo01 {
    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        InetAddress localHostIP = null;
        String localHostName = null;
        try {
            localHostIP = InetAddress.getLocalHost();
            localHostName = localHostIP.getHostName();
            String localIp = InetAddress.getLocalHost().getHostAddress();

            System.out.println("Local IP = " + localIp);

        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException: " + e.getMessage());
        }
        System.out.println("IP = " + localHostIP + " host name = " + localHostName);
    }
}
