package com.cold.tutorial.design.strategy;

/**
 * Created by faker on 2016/5/10.
 */
public class CommonStrategy implements BaseStrategy{
    @Override
    public double computeMoney(double money) {
        return money;
    }
}
