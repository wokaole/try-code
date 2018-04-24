package com.cold.tutorial.concurrent.ex1;

/**
 * 1.创建线程
 * Created by faker on 2015/9/3.
 */
public class Calculator implements Runnable{

    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Calculator c = new Calculator(i);
            Thread t = new Thread(c);
            t.start();
        }
    }
}
