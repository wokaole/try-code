package com.cold.tutorial.concurrent.ex1;

/**
 * Created by faker on 2015/9/3.
 */
public class PrimeGenerator extends Thread{
    @Override
    public void run() {
        long number = 1L;
        while (true) {
            if (isPrime(number)) {
                System.out.println("Number " + number + " is Prime");
            }

            if (isInterrupted()) {
                System.out.println("The Prime Generator has been Interrupted");
                return;
            }
            number++;
        }
    }

    public static void main(String[] args) {
        Thread t = new PrimeGenerator();
        t.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }

    private boolean isPrime(long number) {
        if (number <=2) {
            return true;
        }
        for (long i=2; i<number; i++){
            if ((number % i)==0) {
                return false;
            }
        }
        return true;
    }
}

class Prime implements Runnable {

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) {
                System.out.println("The Prime Generator has been Interrupted");
                return;
            }
        }
    }
}