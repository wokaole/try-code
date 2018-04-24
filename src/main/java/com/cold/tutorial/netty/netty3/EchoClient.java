package com.cold.tutorial.netty.netty3;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author hui.liao
 *         2016/2/23 10:26
 */
public class EchoClient {

    public static void main(String[] args) {
        int port = 8087;

        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        try {
            bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                @Override
                public ChannelPipeline getPipeline() throws Exception {
                    ChannelPipeline p = Channels.pipeline();
                    p.addLast("echo", new EchoClientHandler());
                    return p;
                }
            });

            bootstrap.setOption("tcpNoDelay", true);
            bootstrap.setOption("receiveBufferSize", 1048576);
            bootstrap.setOption("sendBufferSize", 1048576);

            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", port));
            future.getChannel().getCloseFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bootstrap.releaseExternalResources();
        }
    }
}
