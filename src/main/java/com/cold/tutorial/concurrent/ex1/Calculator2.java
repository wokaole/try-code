package com.cold.tutorial.concurrent.ex1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 1.创建线程
 * Created by faker on 2015/9/3.
 */
public class Calculator2 implements Runnable{

    private int number;

    public Calculator2(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }

    public static void main(String[] args) throws IOException {
        Thread[] threads = new Thread[10];
        Thread.State[] states = new Thread.State[10];

        for (int i = 0; i<10; i++) {
            threads[i] = new Thread(new Calculator2(i));
            if (i%2 == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("Thread " + i);
        }

        FileWriter file = new FileWriter("d:\\tmp\\log.txt");
        PrintWriter pw = new PrintWriter(file);

        for (int i = 0; i<10; i++) {
            states[i] = threads[i].getState();
            pw.println("Main : Status of Thread "+i+" : " + states[i]);
        }

        for (int i = 0; i<10; i++) {
            threads[i].start();
        }

        boolean finish=false;
        while (!finish) {
            for (int i=0; i<10; i++){
                if (threads[i].getState()!=states[i]) {
                    writeThreadInfo(pw, threads[i],states[i]);
                    states[i]=threads[i].getState();
                }
            }

            finish=true;
            for (int i=0; i<10; i++){
                finish=finish && (states[i]== Thread.State.TERMINATED);
            }
        }

        file.close();
    }

    private static void writeThreadInfo(PrintWriter pw, Thread thread, Thread.State state) {
        pw.printf("Main : Id %d - %s\n",thread.getId(),thread.getName());
        pw.printf("Main : Priority: %d\n",thread.getPriority());
        pw.printf("Main : Old State: %s\n", state);
        pw.printf("Main : New State: %s\n",thread.getState());
        pw.printf("Main : ************************************\n");
    }
}
