package TcpSocketCommProto.src;

import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPServer01 {
    private static int PORT_NUM = 18888;
    private static int ACCEPT_TIMEOUT = 5000;

    public static void main(String[] args) {
        boolean keepRetry = true;

        try {

            ServerSocket serverSocket = new ServerSocket(PORT_NUM);
            serverSocket.setSoTimeout(ACCEPT_TIMEOUT);

            while (keepRetry) {
                try {
                    TCPUtils.Log("Listening for connection ... ");

                    Socket socket = serverSocket.accept();

                    TCPUtils.Log("A client is connected.");

                    final AtomicBoolean isClientDisconnected = new AtomicBoolean(false);

                    ExecutorService receiver = Executors.newSingleThreadExecutor();
                    receiver.execute(() -> {

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
                            TCPUtils.Log("Terminating receiver thread");
                        }

                    });

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
