package com.cold.tutorial.concurrent.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author hui.liao
 *         2016/3/7 11:27
 */
public class BlockingQ2 {
    private Object notEmpty = new Object();
    private Object notFull = new Object();
    private static final int MAX_LENGTH = 10;
    private Queue<String> linkedList= new LinkedList();

    private String take() throws InterruptedException {
        synchronized (notEmpty) {
            if (linkedList.size() == 0) {
                notEmpty.wait();
            }

            synchronized (notFull) {
                if (linkedList.size() == MAX_LENGTH) {
                    notFull.notifyAll();
                }
            }

            return linkedList.poll();
        }
    }

    private void put(String s) throws InterruptedException {
        synchronized (notEmpty) {
            if (linkedList.size() == 0) {
                notEmpty.notifyAll();
            }

            synchronized (notFull) {
                if (linkedList.size() == MAX_LENGTH) {
                    notFull.wait();
                }
            }

            linkedList.add(s);
        }
    }

}
