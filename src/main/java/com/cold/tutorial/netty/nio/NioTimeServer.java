package com.cold.tutorial.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author hui.liao
 *         2016/2/16 16:46
 */
public class NioTimeServer implements Runnable{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public NioTimeServer(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the serverSocket start the server in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while(!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                //不能用keys.foreach ,因为在foreach或者循环中remove，会导致数据有问题
                //因为remove后，size变小，而i变大
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    try {
                        handlerKey(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                try {
                                    key.channel().close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }

                    iterator.remove(); //必须在iterator.next()之后调用
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerKey(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) { //处理新接入客户端的请求信息，进行设置堵塞，注册等操作
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = channel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }

            if (key.isReadable()) { //读取客户端的请求消息
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);

                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String order = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : " + order);

                    String currentTime = "QT".equalsIgnoreCase(order)? new Date().toString(): "BAD ORDER";
                    doWrite(socketChannel, currentTime);
                } else if (readBytes < 0){
                    key.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);

        }
    }
}
