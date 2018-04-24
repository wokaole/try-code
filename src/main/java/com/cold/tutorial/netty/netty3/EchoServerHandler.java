package com.cold.tutorial.netty.netty3;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * @author hui.liao
 *         2016/2/23 10:22
 */
public class EchoServerHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("server:" + e.getMessage());
        e.getChannel().write(e.getMessage());
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println(System.currentTimeMillis() + " client connected");
    }
}
