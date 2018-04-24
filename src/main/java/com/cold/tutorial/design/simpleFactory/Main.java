package com.cold.tutorial.design.simpleFactory;

/**
 * Created by faker on 2016/5/10.
 */
public class Main {

    public static void main(String[] args) {
        Operator operator = OperatorFactory.createOperator("/");
        System.out.println(operator.operate(10, 3));
    }
}
