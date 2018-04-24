package com.cold.tutorial.netty.netty3;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author hui.liao
 *         2016/2/23 10:19
 */
public class EchoServer {

    public static void main(String[] args) {
        int port = 8087;

        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline p = Channels.pipeline();
                p.addLast("echo", new EchoServerHandler());
                return p;
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.receiveBufferSize", 1048576);
        bootstrap.setOption("child.sendBufferSize", 1048576);

        bootstrap.bind(new InetSocketAddress(port));
    }
}
