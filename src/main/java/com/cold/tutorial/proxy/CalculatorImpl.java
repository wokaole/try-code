package com.cold.tutorial.proxy;

/**
 * @author hui.liao
 *         2016/1/26 15:20
 */
public class CalculatorImpl implements Calculator{
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
