package com.cold.tutorial.netty.decode;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.stream.IntStream;

/**
 * @author hui.liao
 *         2016/2/17 17:15
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    private static final String str = "Hello Netty!$_";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        IntStream.range(0, 100).forEach(i -> ctx.writeAndFlush(Unpooled.copiedBuffer(str.getBytes())));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" now receive :" + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
