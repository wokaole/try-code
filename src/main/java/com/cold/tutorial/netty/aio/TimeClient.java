package com.cold.tutorial.netty.aio;

/**
 * @author hui.liao
 *         2016/2/17 11:22
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8082;
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
    }
}
