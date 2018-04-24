package com.cold.tutorial.concurrent.ex1;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by faker on 2015/9/3.
 */
public class FileClock implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s\n", new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.printf("The FileClock has been interrupted");
            }
        }
    }

    public static void main(String[] args) {
        FileClock clock = new FileClock();
        Thread thread = new Thread(clock);
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(5);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (;;) {
            int value = getValue();
            if (compareAndSet("CGLibProxy", value+1, value)) {
                break;
            }
        }
    }

    private static boolean compareAndSet(String name, int value, int expect) {
        return false;
    }

    private static int getValue() {
        return 0;
    }
}
