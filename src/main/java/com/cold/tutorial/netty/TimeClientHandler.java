package com.cold.tutorial.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author hui.liao
 *         2016/2/17 14:57
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private byte[] bytes;
    private final ByteBuf firstMsg;
    private int count;

    public  TimeClientHandler() {
        bytes = ("QT" + System.getProperty("line.separator")).getBytes();
        firstMsg = Unpooled.buffer(bytes.length);
        //firstMsg.writeBytes(bytes);
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf;
        for (int i=0; i<100; i++) {
            byteBuf = Unpooled.buffer(bytes.length);
            byteBuf.writeBytes(bytes);
            ctx.writeAndFlush(byteBuf);
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
//        byte[] bytes = new byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(bytes);
//        String content = new String(bytes, "UTF-8");

        //TimeClient add解码器后，不需要像上面一样转化
        String content = (String) msg;
        System.out.println("content is :" + content + "; count is " + ++count);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
