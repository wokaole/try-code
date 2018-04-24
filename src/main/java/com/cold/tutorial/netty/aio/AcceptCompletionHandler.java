package com.cold.tutorial.netty.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @author hui.liao
 *         2016/2/17 11:06
 */
public class AcceptCompletionHandler implements java.nio.channels.CompletionHandler<java.nio.channels.AsynchronousSocketChannel, AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.server.accept(attachment, this);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        result.read(byteBuffer, byteBuffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
