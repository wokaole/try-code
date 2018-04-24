package com.cold.tutorial.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


/**
 * @author hui.liao
 *         2016/2/17 14:29
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //add 解码器，解决TCP粘包
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        socketChannel.pipeline().addLast(new StringDecoder());

        socketChannel.pipeline().addLast(new TimeServerHandler());
    }
}
