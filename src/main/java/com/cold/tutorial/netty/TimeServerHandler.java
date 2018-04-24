package com.cold.tutorial.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author hui.liao
 *         2016/2/17 14:39
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] bytes = new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
//
//        String content = new String(bytes, "UTF-8");
//        content = content.substring(0, bytes.length - System.getProperty("line.separator").length());

        //ChildChannelHandler add解码器后，不需要像上面一样转化
        String content = (String) msg;
        System.out.println("The time server receive order : " + content + ", the count is :" + ++counter);
        String currentTime = "QT".equalsIgnoreCase(content)? new Date().toString(): "BAD ORDER";

        ByteBuf byteBuf = Unpooled.copiedBuffer((currentTime + System.getProperty("line.separator")).getBytes());
        ctx.write(byteBuf);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
