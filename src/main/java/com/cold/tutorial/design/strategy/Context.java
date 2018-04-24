package com.cold.tutorial.design.strategy;

/**
 * Created by faker on 2016/5/10.
 */
public class Context {
    private BaseStrategy baseStrategy;

    public Context(String type) {
        switch (type) {
            case "1":
                baseStrategy = new CommonStrategy();
                break;
            case "2":
                baseStrategy = new RebateStrategy(0.8);
                break;
            case "3":
                baseStrategy = new ReturnStrategy(300, 20);
                break;
        }
    }

    public double computeMoney(double money) {
        return baseStrategy.computeMoney(money);
    }
}
