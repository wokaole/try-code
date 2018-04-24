package com.cold.tutorial.concurrent.threadpool;

import java.util.concurrent.*;

/**
 * @author hui.liao
 *         2016/1/26 15:10
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newSingleThreadExecutor();
        Future<Object> future = pool.submit((Callable<Object>) () -> {
            TimeUnit.SECONDS.sleep(5);
            return "ok";
        });

        System.out.println("doing something");

        System.out.println(future.get());

    }
}
