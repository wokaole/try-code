package com.cold.tutorial.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 在堵塞队列用，要使用put和take ，而不是offer和poll，因为offer和poll不等待就直接返回，是继承queue接口的方法，没有blocking特性
 * 或者使用带时间参数的offer和poll方法
 * @author hui.liao
 *         2016/3/7 11:01
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        new Thread(() -> {
            for(;;) {
                String s = queue.poll();  //不等待就直接返回，
                try {
                    s = queue.poll(3, TimeUnit.SECONDS); //带时间参数，会等待设置的时间
                    //s = queue.take(); //会堵塞
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(s);
            }
        }).start();
    }
}
