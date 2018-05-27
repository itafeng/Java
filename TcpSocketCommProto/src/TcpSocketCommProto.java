package TcpSocketCommProto.src;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpSocketCommProto {
    static boolean isServer;
    static String serverHost;
    static final int SERVER_PORT = 18888;

    static ExecutorService communicationWorker;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: TcpSocketCommProto -server=true|false -serverHost=server");
            return;
        }

        isServer = Boolean.parseBoolean(args[0].split("=")[1]);
        serverHost = args[1].split("=")[1].trim();
        System.out.println(String.format("Starting %s ...", isServer ? "server" : "client"));
        System.out.println("Server is " + serverHost);

        startInterhostCommunicationWorker(isServer, serverHost);
    }

    private static void startInterhostCommunicationWorker(boolean isServer, String serverHost) {
        TcpCommunicationManager connectionMgr = new TcpCommunicationManager();

        if (isServer) {
            try {
                connectionMgr.startServer(SERVER_PORT);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            try {
                connectionMgr.connectToServer(serverHost, SERVER_PORT);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


}
