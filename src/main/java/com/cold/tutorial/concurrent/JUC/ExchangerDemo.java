package com.cold.tutorial.concurrent.JUC;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *线程之间交互数据，且在并发时候使用，两两交换，交换中不会因为线程多而混乱，发送出去没接收到会一直等，由交互器完成交互过程。
 * Created by hui.liao on 2015/11/26.
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        final Exchanger<Integer> exchanger = new Exchanger<>();

        for (int i=0; i<10; i++) {
            final int num = i;

            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread :" + Thread.currentThread().getName() + " data is:" + num);
                    try {
                        Integer exchange = exchanger.exchange(num);
                        Thread.sleep(1000);
                        System.out.println("thread :" + Thread.currentThread().getName() +
                                " exchanger data is:" + exchange + " ;orginal data is :" +num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
