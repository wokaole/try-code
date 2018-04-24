package com.cold.tutorial.concurrent.forkjoin;

import java.util.concurrent.*;

/**
 * Created by hui.liao on 2015/9/14.
 */
public class CountTask extends RecursiveTask<Integer>{

    private static final int THRESHOLD = 2;

    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        int sum = 0;

        boolean isCompute = ((end - start) <= THRESHOLD);
        if (isCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = ((end + start)/2);
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            leftTask.fork();
            rightTask.fork();

            int leftSum = leftTask.join();
            int rightSum = rightTask.join();
            sum = leftSum + rightSum;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(1, 55);
        Future<Integer> submit = pool.submit(task);

        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
