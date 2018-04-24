package com.cold.tutorial.netty.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hui.liao
 *         2016/2/23 10:31
 */
public class EchoClientHandler extends SimpleChannelUpstreamHandler {

    private final ChannelBuffer message;
    private final AtomicLong transferredBytes = new AtomicLong();

    public EchoClientHandler() {
        message = ChannelBuffers.buffer(256);
        for (int i=0; i<message.capacity(); i++) {
            message.writeByte((byte)i);
        }

    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        transferredBytes.addAndGet(((ChannelBuffer) e.getMessage()).readableBytes());
        System.out.println("transferredBytes : " + transferredBytes.get());
        System.out.println("client:" + e.getMessage());
        //e.getChannel().write(e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println(System.currentTimeMillis() + " channelConnected " + message.capacity());
        e.getChannel().write(message);
    }
}
