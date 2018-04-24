package com.cold.tutorial.design.simpleFactory;

/**
 * Created by faker on 2016/5/10.
 */
public abstract class Operator {
    private long num1;
    private long num2;

    public abstract long operate(long num1, long num2);

    public long getNum1() {
        return num1;
    }

    public void setNum1(long num1) {
        this.num1 = num1;
    }

    public long getNum2() {
        return num2;
    }

    public void setNum2(long num2) {
        this.num2 = num2;
    }
}
