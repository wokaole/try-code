package com.cold.tutorial.netty.nio;

/**
 * @author hui.liao
 *         2016/2/16 16:41
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8081;

        NioTimeServer timeServer = new NioTimeServer(port);
        new Thread(timeServer, "NIO-NioTimeServer-001").start();
    }
}
