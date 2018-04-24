package com.cold.tutorial.concurrent.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author hui.liao
 *         2016/3/4 10:20
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").setDaemon(false).build();
        ExecutorService pool = Executors.newFixedThreadPool(3, factory);
        IntStream.range(0, 3).forEach(i -> {
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        });

        System.out.println(pool.isShutdown());
        System.out.println(pool.isTerminated());
        pool.shutdown();
        System.out.println(pool.isShutdown());
        System.out.println(pool.isTerminated());
        try {
            boolean isTermination = pool.awaitTermination(3, TimeUnit.SECONDS);
            System.out.println(isTermination);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(pool.isShutdown());
        System.out.println(pool.isTerminated());
    }
}
