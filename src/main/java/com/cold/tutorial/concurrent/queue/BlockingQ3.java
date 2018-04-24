package com.cold.tutorial.concurrent.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hui.liao
 *         2016/3/7 12:43
 */
public class BlockingQ3 {

    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();
    private Queue<String> linkedList= new LinkedList();
    private static final int MAX_LENGTH = 10;

    private String take() throws InterruptedException {
        try {
            lock.lock();
            if (linkedList.size() == 0) {
                notEmpty.await();
            }
            if (linkedList.size() == MAX_LENGTH) {
                notFull.signalAll();
            }
            return linkedList.poll();
        } finally {
            lock.unlock();
        }
    }

    private void put(String s) {
        try {
            lock.lock();
            if (linkedList.size() == 0) {
                notEmpty.signalAll();
            }
            if (linkedList.size() == MAX_LENGTH) {
                notFull.await();
            }
            linkedList.add(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
