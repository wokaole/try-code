package com.cold.tutorial.concurrent.JUC;

import org.joda.time.DateTime;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * Created by hui.liao on 2015/11/26.
 */
public class SemaphoreDemo {

    //最多只能acquire 5次，即5个同时执行，然后需要release，用来控制访问速度
    private final static Semaphore SEMAPHORE = new Semaphore(5);

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (int i=0; i<50; i++) {
            final int num = i;
            final Random random = new Random();
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    boolean acquire = false;
                    try {
                        SEMAPHORE.acquire();
                        acquire = true;
                        System.out.println("thread :" + num + " get! " + DateTime.now().toString("yyyyMMdd HH:mm:ss"));
                        int sleepTime = 1000 * Math.max(1, Math.abs(random.nextInt() % 10));
                        Thread.sleep(sleepTime);
                        System.out.println("thread :" + num + " end! " + DateTime.now().toString("yyyyMMdd HH:mm:ss"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (acquire) {
                            SEMAPHORE.release();
                        }
                    }
                }
            });
        }
    }
}
