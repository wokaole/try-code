package com.cold.tutorial.cache;

import java.util.concurrent.*;

/**
 * @author faker
 * @date 2017/5/9 12:26.
 */
public class FutureCache<K, V>  implements Computable<K,V> {

    private ConcurrentHashMap<K, FutureTask<V>> cache = new ConcurrentHashMap<>();
    private final Computable<K, V> c;
    public FutureCache(Computable<K, V> c) {
        this.c = c;
    }

    public V compute(K key) throws InterruptedException {
        while (true) {
            FutureTask<V> value = cache.get(key);
            if (value == null) {
                FutureTask<V> futureTask = new FutureTask<>(() -> c.compute(key));
                //使用putIfAbsent避免多线程同时进入value == null 引起竞态条件
                value = cache.putIfAbsent(key, futureTask);
                if (value == null) {
                    value = futureTask;
                    value.run();
                }
            }

            try {
                return value.get();
            } catch (CancellationException e) {
                // 当计算被取消时, 从缓存中移除键值对， 防止缓存污染
                cache.remove(key, value);
            } catch (ExecutionException e) {
                //执行中的异常应当抛出，获得恰当处理
                throw launderThrowable(e.getCause());
            }
        }
    }

    private RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException)t;
        } else if (t instanceof Error) {
            throw (Error)t;
        } else {
            throw new IllegalArgumentException("Not unchecked");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Computable<Integer, Integer> c = new Computable<Integer, Integer>() {
            @Override
            public Integer compute(Integer arg) {
                Integer sum = 0;
                for(Integer i=0;i<arg;i++){
                    sum+=i;
                }
                return sum;
            }
        };
        final Computable<Integer, Integer> cache = new FutureCache<Integer,Integer>(c);
        long start = System.currentTimeMillis();
        cache.compute(10000);
        long stop = System.currentTimeMillis();
        System.out.println(stop-start);
        start = System.currentTimeMillis();
        cache.compute(10000);
        stop = System.currentTimeMillis();
        System.out.println(stop-start);
        start = System.currentTimeMillis();
        cache.compute(10000);
        stop = System.currentTimeMillis();
        System.out.println(stop-start);
        start = System.currentTimeMillis();
        cache.compute(10000);
        stop = System.currentTimeMillis();
        System.out.println(stop-start);
    }

}
