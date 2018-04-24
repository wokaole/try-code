package com.cold.tutorial.concurrent.ex1;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Thread 类的join() 方法。当前线程调用某个线程的join()方法时，它会暂停当前线程，直到被调用线程执行完成。
 * Created by faker on 2015/9/3.
 */
public class ThreadJoin {



    public static void main(String[] args) {
        Thread t1 = new Thread(new Join1());
        Thread t2 = new Thread(new Join2());

        t1.start();
        t2.start();

        try {
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main method end...");
    }
}

class Join1 implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Join1 method end...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Join2 implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(7);
            System.out.println("Join2 method end...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}