package TcpSocketCommProto.src;

import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ConnectException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;

public class TCPClient02 {
    private static int PORT_NUM = 18888;
    private static String LOCAL_HOST = "127.0.0.1";
    private static int sequence = 0;

    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: TCPClient01 client_name");
        }

        String clientName = args[0];

        try {
            Socket socket = new Socket(LOCAL_HOST, PORT_NUM);

            ScheduledExecutorService sender = Executors.newSingleThreadScheduledExecutor();
            ExecutorService receiver = Executors.newSingleThreadExecutor();


            sender.scheduleAtFixedRate(() -> {
                try {
                    PrintWriter sendBuffer = new PrintWriter(socket.getOutputStream());

                    String message = String.format("[Src: %s] - sequence number = %d", clientName, sequence);
                    sendBuffer.println(message);
                    sendBuffer.flush();

                    TCPUtils.Log("Message sent: " + message);

                    ++sequence;

                } catch (IOException ie) {
                    TCPUtils.Log("IOException caught - 1: " + ie);
                }
            }, 0, 2, TimeUnit.SECONDS);


            receiver.execute(() -> {
                try {
                    BufferedReader receiveBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    while (true) {
                        String incomingMsg = receiveBuffer.readLine();

                        if (Strings.isNullOrEmpty(incomingMsg)) {
                            TCPUtils.Log("Server is disconnected");
                            throw new IOException("Server is disconnected");
                        }

                        TCPUtils.Log("Message received:  " + incomingMsg);
                    }

                } catch (IOException ie) {
                    TCPUtils.Log("IOException caught: " + ie);
                }
            });


        } catch (ConnectException ce) {
            TCPUtils.Log("ConnectException caught - 0: " + ce);
        } catch (IOException io) {
            TCPUtils.Log("IOException caught - 0: " + io);
        }

        TCPUtils.Log("Exit TCPClient01::main()");

    }
}
