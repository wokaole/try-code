package com.cold.tutorial.proxy;

/**
 * @author hui.liao
 *         2016/1/26 15:21
 */
public class CalculatorProxy {
    public Calculator calculator;

    public CalculatorProxy(Calculator calculator) {
        this.calculator = calculator;
    }

    public int add(int a, int b) {
        System.out.println("do something before");
        int r = calculator.add(a, b);
        System.out.println("do something after");
        return r;
    }

    public static void main(String[] args) {
        CalculatorProxy proxy = new CalculatorProxy(new CalculatorImpl());
        System.out.println(proxy.add(3, 4));
    }

}
