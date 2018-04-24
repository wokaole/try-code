package com.cold.tutorial.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author hui.liao
 *         2016/2/15 11:29
 */
public class Server {

    ServerBootstrap bootstrap;
    Channel parentChannel;
    InetSocketAddress localAddress;
    MyChannelHandler channelHandler = new MyChannelHandler();

    public Server() {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setOption("reuseAddress", true);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.soLinger", 2);
        bootstrap.getPipeline().addLast("servercnfactory", channelHandler);
    }

    public void config(int port) {
        this.localAddress = new InetSocketAddress(port);
    }

    public void start() {
        parentChannel = bootstrap.bind(localAddress);
    }
}
