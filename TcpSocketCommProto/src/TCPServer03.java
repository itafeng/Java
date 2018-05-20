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
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Data;

public class TCPServer03 {
    private static int PORT_NUM = 18888;
    private static int ACCEPT_TIMEOUT = 5000;

    @Data
    @AllArgsConstructor
    private static class ThreadMetadata {
        Socket socket;
        ScheduledFuture<?> timerThreadResult;
    }

    private static ConcurrentMap<Integer, ThreadMetadata> connectionTable = new ConcurrentHashMap<>();

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

            AtomicInteger id = new AtomicInteger(0);

            while (keepRetry) {
                try {
                    TCPUtils.Log("Listening for connection ... ");

                    Socket socket = serverSocket.accept();

                    TCPUtils.Log("A client is connected.");

                    final AtomicBoolean isClientDisconnected = new AtomicBoolean(false);

                    receiverPool.execute(() -> {

                        int new_id = id.get();

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
                            TCPUtils.Log("Closing socket ... ");
                            try {
                                if (!socket.isClosed()) {
                                    socket.close();
                                }
                                TCPUtils.Log("Is socket closed : " + socket.isClosed());

                                TCPUtils.Log("Cancelling receiver thread ... ");

                                TCPUtils.Log("cancelled ID = " + id.get() + ", cancelled new ID = " + new_id);


                            } catch (IOException ioe) {}

                            TCPUtils.Log("Terminating receiver thread");
                        }

                    });

                    ScheduledFuture<?> timer = senderPool.scheduleAtFixedRate(() -> {
                        try {

                            Socket tmp = socket;

                            TCPUtils.Log("Executing sender thread ...");

                            PrintWriter sendBuffer = new PrintWriter(tmp.getOutputStream());

                            String message = "[Src: Server] - Greeting from server";
                            sendBuffer.println(message);
                            sendBuffer.flush();
                            TCPUtils.Log("Message sent:  " + message);

                        } catch (IOException ie) {
                            TCPUtils.Log("IOException caught - 3: " + ie);
                        } catch (Throwable tr) {
                            TCPUtils.Log("Throwable caught: " + tr);
                        }



                    }, 0, 2, TimeUnit.SECONDS);


                    connectionTable.put(id.get(), new ThreadMetadata(socket, timer));

                    id.set(id.get() + 1);


                } catch (SocketTimeoutException ste) {
                    TCPUtils.Log(String.format("SocketTimeoutException caught - 1: ", ste));

                    for (Map.Entry<Integer, ThreadMetadata> entry : connectionTable.entrySet()) {
                        System.out.println(String.format("Key = %d, Thread.socket.isclosed() = %s, Thread.timerThreadResult.isCancelled() = %s", entry.getKey(),
                                entry.getValue().getSocket().isClosed(), entry.getValue().getTimerThreadResult().isCancelled()));

                        if (entry.getValue().getSocket().isClosed()) {
                            connectionTable.remove(entry.getKey());
                        }

                    }



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
