package com.cold.tutorial.netty.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * @author hui.liao
 *         2016/2/17 11:11
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel socketChannel;


    public ReadCompletionHandler(AsynchronousSocketChannel result) {
        if (this.socketChannel == null) {
            this.socketChannel = result;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);

        try {
            String content = new String(bytes, "UTF-8");
            System.out.println("The time server receive order : " + content);

            String currentTime = "QT".equalsIgnoreCase(content)? new Date().toString(): "BAD ORDER";
            doWrite(currentTime);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void doWrite(String currentTime) {
        if (currentTime != null && currentTime.trim().length() > 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (attachment.hasRemaining()) {
                        socketChannel.write(attachment, attachment, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
