package com.cold.tutorial.design.strategy;

/**
 * Created by faker on 2016/5/10.
 */
public class RebateStrategy implements BaseStrategy{

    private double rebate;

    public RebateStrategy(double rebate) {
        this.rebate = rebate;
    }

    @Override
    public double computeMoney(double money) {
        return money * rebate;
    }

    public double getRebate() {
        return rebate;
    }

    public void setRebate(double rebate) {
        this.rebate = rebate;
    }
}
