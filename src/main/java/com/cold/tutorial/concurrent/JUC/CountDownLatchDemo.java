package com.cold.tutorial.concurrent.JUC;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 计数器，只能使用一次
 * Created by hui.liao on 2015/11/26.
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(5);
        final Random random = new Random();
        for (int i=0; i<5; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("thread:" + Thread.currentThread().getName() + " is ready!");
                        startLatch.await();

                        int sleepTime = 1000 * Math.max(1, Math.abs(random.nextInt() % 10));
                        Thread.sleep(sleepTime);
                        System.out.println("thread:" + Thread.currentThread().getName() + " is goal!");
                        endLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        System.out.println("game start:");
        startLatch.countDown();

        threadPool.shutdown();
    }
}
