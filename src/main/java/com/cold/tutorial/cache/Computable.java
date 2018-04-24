package com.cold.tutorial.cache;

/**
 * @author liaowenhui
 * @date 2017/5/9 12:55.
 */
@FunctionalInterface
public interface Computable<K, V> {

    V compute(K k) throws InterruptedException;
}
