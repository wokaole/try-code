package com.cold.tutorial.netty.nio;

import java.util.concurrent.TimeUnit;

/**
 * @author hui.liao
 *         2016/2/16 17:36
 */
public class TimeClient {
    public static void main(String[] args) throws InterruptedException {
        int port = 8081;
        TimeUnit.SECONDS.sleep(5);
        new Thread(new TimeClientHandler("127.0.0.1", port), "TimeClient-001").start();
    }
}
