package com.cold.tutorial.design.strategy;

/**
 * Created by faker on 2016/5/10.
 */
public class Main {

    public static void main(String[] args) {
        Context context = new Context("3");
        double money = context.computeMoney(100);
        System.out.println(money);
    }
}
