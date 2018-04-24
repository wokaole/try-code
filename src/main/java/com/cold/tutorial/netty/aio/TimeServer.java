package com.cold.tutorial.netty.aio;

import java.io.IOException;

/**
 * @author hui.liao
 *         2016/2/17 10:59
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8082;
        AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(timeServerHandler, "AIO-TimeServer-001").start();
    }
}
