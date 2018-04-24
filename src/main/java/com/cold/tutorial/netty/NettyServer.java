package com.cold.tutorial.netty;

/**
 * @author hui.liao
 *         2016/2/15 11:29
 */
public class NettyServer {

    public final static int port = 8080;

    public static void main(String[] args) {
        Server server = new Server();
        server.config(port);
        server.start();
    }
}
