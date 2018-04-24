package com.cold.tutorial.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author hui.liao
 *         2016/1/26 14:27
 */
public class CostTimeCompare {

    public static void useThreadPool() throws InterruptedException {
        int count = 50000;
        ExecutorService pool = Executors.newFixedThreadPool(50000);
        Random random = new Random();
        List<Integer> list = new ArrayList<>();

        IntStream.range(0, count).forEach((i) -> pool.execute(() -> {
            list.add(random.nextInt());
        }));

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(list.size());
    }

    public static void noUseThreadPool() {
        int count = 50000;
        Random random = new Random();
        List<Integer> list = new ArrayList<>();

        IntStream.range(0, count).forEach((i) -> {
            Thread thread = new Thread(() -> {
                list.add(random.nextInt());
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(list.size());
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        noUseThreadPool();
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        useThreadPool();
        System.out.println(System.currentTimeMillis() - end);

    }
}
