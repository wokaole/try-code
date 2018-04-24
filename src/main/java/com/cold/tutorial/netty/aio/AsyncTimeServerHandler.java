package com.cold.tutorial.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author hui.liao
 *         2016/2/17 11:01
 */
public class AsyncTimeServerHandler implements Runnable{

    int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel server;

    public AsyncTimeServerHandler(int port) throws IOException {
        this.port = port;
        server = AsynchronousServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        System.out.println("The time server start...");
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);

        doAccept();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        server.accept(this, new AcceptCompletionHandler());
    }
}
