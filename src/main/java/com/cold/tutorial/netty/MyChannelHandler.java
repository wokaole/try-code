package com.cold.tutorial.netty;

import org.jboss.netty.channel.*;

/**
 * @author hui.liao
 *         2016/2/15 11:35
 */
public class MyChannelHandler extends SimpleChannelHandler{
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Channel connected " + e);
    }

    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Channel open " + e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        try {
            System.out.println("New message " + e.toString() + " from "
                    + ctx.getChannel());
            processMessage(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private void processMessage(MessageEvent e) {
        Channel ch = e.getChannel();
        ch.write(e.getMessage());
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Channel closed " + e);
    }
}
