package Networking.src.com.itnovice.java.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TCPServerSelector {
    private static final int BUFSIZE = 256;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            throw new IllegalArgumentException("Parameter(s): <Port> ...");
        }

        // Create a selector to multiplex listening sockets and connections
        Selector selector = Selector.open();

        // Create listening socket channel for each port and register selector
        for (String arg: args) {
            ServerSocketChannel listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(Integer.parseInt(arg)));
            listenChannel.configureBlocking(false);

            // Register selector with channel. The returned key is ignored
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        // Create a handler that will implement the protocol
        TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);

        while (true) {
            // Run forever, processing available I/O operations
            // Wait for some channel to be ready
            if (selector.select(TIMEOUT) == 0) {
                System.out.print(".");
                continue;
            }

            // Get iterator on set of keys with I/O to process
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();

                // Server socket channel has pending connection request?
                if (key.isAcceptable()) {
                    protocol.handleAccept(key);
                }

                // Client socket channel has pending data?
                if (key.isReadable()) {
                    protocol.handleRead(key);
                }

                // Client socket channel is available is available for writing and key is valid (i.e., channel is not closed) ?

                if (key.isValid() && key.isWritable()) {
                    protocol.handleWrite(key);
                }

                keyIter.remove();
            }


        }


    }
}
