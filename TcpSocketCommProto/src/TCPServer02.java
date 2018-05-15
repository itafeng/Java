package TcpSocketCommProto.src;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPServer02 {
    private static int PORT_NUM = 18888;
    private static int ACCEPT_TIMEOUT = 5000;

    public static void main(String[] args) {
        boolean keepRetry = true;

        try {

            ServerSocket serverSocket = new ServerSocket(PORT_NUM);
            serverSocket.setSoTimeout(ACCEPT_TIMEOUT);

            final Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    TCPUtils.Log(String.format("Uncaught exception: thread: %s, exception: %s", t.getName(), e));
                }
            };

            ExecutorService receiverPool = Executors.newFixedThreadPool(10, new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .setNameFormat("TCPServerReciverPool-%d")
                    .setUncaughtExceptionHandler(handler)
                    .build());

            ScheduledExecutorService senderPool = Executors.newScheduledThreadPool(10);

            while (keepRetry) {
                try {
                    TCPUtils.Log("Listening for connection ... ");

                    Socket socket = serverSocket.accept();

                    TCPUtils.Log("A client is connected.");

                    final AtomicBoolean isClientDisconnected = new AtomicBoolean(false);

                    receiverPool.execute(() -> {

                        try {

                            BufferedReader recv = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            while (true) {
                                String incomingMsg = recv.readLine();

                                if (Strings.isNullOrEmpty(incomingMsg)) {
                                    TCPUtils.Log("Client is disconnected");
                                    throw new IOException("Client is disconnected");
                                }

                                TCPUtils.Log("Message received:  " + incomingMsg);
                            }
                        } catch (IOException io) {
                            TCPUtils.Log("IOException Caught - 0: " + io);

                        } finally {
                            isClientDisconnected.set(true);
                            TCPUtils.Log("Closing socket ... ");

                            try {
                                TCPUtils.Log("Is socket closed : " + socket.isClosed());
                                if (!socket.isClosed()) {
                                    socket.close();
                                }
                                TCPUtils.Log("Is socket closed : " + socket.isClosed());
                            } catch (IOException ioe) {}

                            TCPUtils.Log("Terminating receiver thread");
                        }

                    });


                    ScheduledFuture<?> timer = senderPool.scheduleAtFixedRate(() -> {
                        try {
                            PrintWriter sendBuffer = new PrintWriter(socket.getOutputStream());

                            String message = String.format("[Src: Server] - sequence number = %d", "Greeting from server");
                            sendBuffer.println(message);
                            sendBuffer.flush();

                        } catch (IOException ie) {
                            TCPUtils.Log("IOException caught - 3: " + ie);
                        }
                    }, 0, 2, TimeUnit.SECONDS);



                } catch (SocketTimeoutException ste) {
                    TCPUtils.Log(String.format("SocketTimeoutException caught - 1: ", ste));
                } catch (IOException ie) {
                    TCPUtils.Log(String.format("IOException caught - 1: ", ie));
                }
            }
        } catch (SocketException se) {
            TCPUtils.Log("SocektException caught - 2: " + se);
        } catch (IOException ie) {
            TCPUtils.Log("IOException caught - 2: " + ie );
        }

    }
}
