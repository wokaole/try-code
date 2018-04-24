package com.cold.tutorial.design.simpleFactory;

/**
 * Created by faker on 2016/5/10.
 */
public class OperatorSub extends Operator{
    @Override
    public long operate(long num1, long num2) {
        return num1 - num2;
    }
}
