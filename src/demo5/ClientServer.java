package demo5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ClientServer {
    private int port;
    private ServerSocketChannel serverSocket;

    public ClientServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = ServerSocketChannel.open();
        this.serverSocket.socket().bind(new InetSocketAddress(port), 1024);
    }

    public void start(int threads) {
        for (int i = 0; i < threads; i++) {
            new SendThread().start();
            new ReceiveThread().start();
        }
    }

    void close(SocketChannel channel) {
        try {
            if (channel != null) channel.close();
        } catch (IOException e1) {
            // ignore
        }
    }

    class SendThread extends Thread {
        @Override
        public void run() {
            SocketChannel channel = null;
            try {
                channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
                for (int i = 0; i < 100; i++) {
                    byte[] data = new byte[100000 * i];
                    channel.write(ByteBuffer.wrap(data));
                }
                channel.close();
            } catch (Throwable e) {
                close(channel);
            }
        }
    }

    class ReceiveThread extends Thread {
        @Override
        public void run() {
            SocketChannel channel = null;
            try {
                channel = serverSocket.accept();
                ByteBuffer buffer = ByteBuffer.allocate(65536);
                while (!isInterrupted()) {
                    channel.read(buffer);
                    buffer.clear();
                }
            } catch (Throwable e) {
                close(channel);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ClientServer(5555).start(32);
    }
}
