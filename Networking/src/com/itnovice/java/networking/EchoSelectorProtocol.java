package Networking.src.com.itnovice.java.networking;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoSelectorProtocol implements TCPProtocol {
    private int bufSize;

    public EchoSelectorProtocol(int bufSize) {
        this.bufSize = bufSize;
    }

    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
        clientChannel.configureBlocking(false);    // Must be nonblocking to register

        // Register the selector with new channel for read and attach byte buffer
        clientChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));

    }


    public void handleRead(SelectionKey key) throws IOException {
        // Client socket channel has pending data

        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        long bytesRead = clientChannel.read(buffer);
        if (bytesRead == -1) {
            // Did the other end close?
            clientChannel.close();
        } else if (bytesRead > 0) {
            // Indicate via key that reading/writing are both of interest now
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }

    }

    public void handleWrite(SelectionKey key) throws IOException {
        // Channel is available for writing, and key is valid (i.e., client channel not closed

        // Retrieve data read earlier
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();  // prepare buffer for writing

        SocketChannel clientChannel = (SocketChannel) key.channel();
        clientChannel.write(buffer);

        if (!buffer.hasRemaining()) {
            // Buffer completely written?
            // Nothing left, so no longer interested in writes
            key.interestOps(SelectionKey.OP_READ);
        }

        buffer.compact();   // Make room for more data to be read in

    }


}
