package com.cold.tutorial.netty.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hui.liao
 *         2016/2/16 15:37
 */
public class TimeServerHandlerExecutePool {

    public ExecutorService executor;

    public TimeServerHandlerExecutePool(int maxPoolSize, int maxQueueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(maxQueueSize));
    }

    public void execute(TimeServerHandler timeServerHandler) {
        executor.execute(timeServerHandler);
    }
}
