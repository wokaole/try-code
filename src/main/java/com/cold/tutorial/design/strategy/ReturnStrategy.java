package com.cold.tutorial.design.strategy;

/**
 * Created by faker on 2016/5/10.
 */
public class ReturnStrategy implements BaseStrategy{

    private double money;
    private double returnMoney;

    public ReturnStrategy(double money, double returnMoney) {
        this.money = money;
        this.returnMoney = returnMoney;
    }

    @Override
    public double computeMoney(double money) {
        if (money > this.money) {
            return money - 20;
        }
        return money - 10;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(double returnMoney) {
        this.returnMoney = returnMoney;
    }
}
