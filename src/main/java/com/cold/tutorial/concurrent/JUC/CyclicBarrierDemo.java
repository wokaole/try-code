package com.cold.tutorial.concurrent.JUC;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier：循环关卡
 * 与CountDownLatch用法差不多，唯一的区别是，CountDownLatch是一次性的，而CyclicBarrier可以重复用
 * Created by hui.liao on 2015/11/26.
 */
public class CyclicBarrierDemo {


    public static void main(String[] args) {
        final CyclicBarrier  CYCLIC_BARRIER = new CyclicBarrier(10);
        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (int i=0; i<10; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("我是线程：" + Thread.currentThread().getName() + " 我们达到旅游地点！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + Thread.currentThread().getName() + " 我开始骑车！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + Thread.currentThread().getName() + " 我们开始爬山！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + Thread.currentThread().getName() + " 我们回宾馆休息！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + Thread.currentThread().getName() + " 我们开始乘车回家！");
                        CYCLIC_BARRIER.await();
                        System.out.println("我是线程：" + Thread.currentThread().getName() + " 我们到家了！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        threadPool.shutdown();
    }
}
