package com.cold.tutorial.soa;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author hui.liao
 *         2016/1/20 17:43
 */
public class SemaphoreDemo {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(100);

        ExecutorService pool = Executors.newFixedThreadPool(150);
        CountDownLatch latch = new CountDownLatch(150);
        IntStream.range(0, 150).forEach((i) -> {

                pool.execute(() -> {

                    try {
                        latch.await();
                        if (semaphore.getQueueLength() > 40) {
                            System.out.println(semaphore.getQueueLength());
                            System.out.println("semaphore length > 40, " + i);
                            return;
                        }
                        semaphore.acquire();
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                });

            latch.countDown();
                }

        );

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }
}
