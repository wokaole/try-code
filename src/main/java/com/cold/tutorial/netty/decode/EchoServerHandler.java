package com.cold.tutorial.netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author hui.liao
 *         2016/2/17 16:49
 */
public class EchoServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String content = (String) msg;
        System.out.println("content is : " + content);
        content += "$_";

        ByteBuf byteBuf = Unpooled.copiedBuffer(content.getBytes());
        ctx.writeAndFlush(byteBuf);
    }
}
