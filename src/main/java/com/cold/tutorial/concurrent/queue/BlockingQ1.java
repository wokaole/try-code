package com.cold.tutorial.concurrent.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 简单的实现堵塞队列1
 * @author hui.liao
 *         2016/3/7 11:11
 */
public class BlockingQ1 {

    private Object notEmpty = new Object();
    private Queue<String> linkedList= new LinkedList();

    private void put(String s) {
        synchronized (notEmpty) {
            if (linkedList.size() == 0) {
                notEmpty.notifyAll();
            }
            linkedList.add(s);
        }
    }

    private String take() throws InterruptedException {
        synchronized (notEmpty) {
            if (linkedList.size() == 0) {
                //调用wait后会释放锁
                //调用对象的wait、notify、notifyAll 方法必须先获得锁
                notEmpty.wait();
            }
            return linkedList.poll();
        }
    }

}
