package TcpSocketCommProto.src;

import com.sun.deploy.util.StringUtils;
import jdk.nashorn.internal.ir.ContinueNode;
import sun.rmi.runtime.Log;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.invoke.VolatileCallSite;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class TcpCommunicationManager {
    ConcurrentMap<String, Socket> connectionTable = new ConcurrentHashMap<>();

    public void startServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService receiverPool = Executors.newWorkStealingPool(2);
        ScheduledExecutorService senderPool = Executors.newScheduledThreadPool(2);
        String clientHostName = "";

        while (true) {

            try {
                Socket socket = serverSocket.accept();
                clientHostName = socket.getInetAddress().getHostName();
                connectionTable.put(clientHostName, socket);
                final String client = clientHostName;

                receiverPool.submit(() -> {
                    try {
                        BufferedReader receiveBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String incomingMsg;

                        while (connectionTable.containsKey(client)) {
                            try {
                                incomingMsg = receiveBuffer.readLine();
                                if (incomingMsg == null || incomingMsg.isEmpty()) {
                                    connectionTable.get(client).close();
                                    connectionTable.remove(client);
                                    System.out.println("Client is disconnected1");
//                                    System.out.println("Shutting down sender thread");
//                                    senderPool.shutdown();
                                    continue;
                                }

                                System.out.println("Receiving message from client: " + incomingMsg);

                            } catch (Exception ex) {
                                System.out.println("Server side receive exception: " + ex.getMessage());
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                senderPool.scheduleAtFixedRate(() -> {
                    try {
                        PrintStream sendBuffer = new PrintStream(socket.getOutputStream());
                        String outgoingMsg = String.format("Send message to client: %s ", System.currentTimeMillis());
                        sendBuffer.println(outgoingMsg);
                        System.out.println(outgoingMsg);
                    } catch (IOException ioe) {
                        System.out.println("IOException: " + ioe.getMessage());
                    }
                }, 0, 5, TimeUnit.SECONDS);

            } catch (SocketTimeoutException ste) {
                System.out.println("SocketTimeoutException: " + ste.getMessage());
            } catch (SocketException se) {
                System.out.println("Connection is lost");
                connectionTable.remove(clientHostName);
            }

            System.out.println("I'm here!");
        }
    }

    public void connectToServer(String serverHostName, int port) throws UnknownHostException {

        ScheduledExecutorService senderThread = Executors.newScheduledThreadPool(1);

        Socket clientSocket = null;


        while (true) {

            try {
                clientSocket = new Socket(serverHostName, port);

                final Socket client = clientSocket;

                final boolean[] shouldRetry = {true};

                System.out.println("Connection is established to " + serverHostName);

                senderThread.scheduleAtFixedRate(() -> {
                    try {
                        if (!shouldRetry[0]) {
                            System.out.println("Server is disconnected");
                            senderThread.shutdown();
                        }

                        PrintStream sendBuffer = new PrintStream(client.getOutputStream());
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        String outgoingMsg = String.format("Send message to server: [%s]", formatter.format(new Date()));
                        sendBuffer.println(outgoingMsg);
                        System.out.println(outgoingMsg);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }, 0, 5, TimeUnit.SECONDS);


                BufferedReader receiveBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String incomingMsg;

                while (shouldRetry[0]) {
                    incomingMsg = receiveBuffer.readLine();
                    if (incomingMsg == null || incomingMsg.isEmpty()) {
                        shouldRetry[0] = false;
                        client.close();
                        System.out.println("Server is disconnected");
                        continue;
                    }

                    System.out.println("Receive message from server: " + incomingMsg);

                }

            } catch (UnknownHostException ukh) {
                throw new RuntimeException("Unknown host name: " + ukh.getMessage());
            } catch (IOException ioe) {
                System.out.println("IOException: " + ioe.getMessage());
            }
        }
    }
}
