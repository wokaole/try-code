package com.cold.tutorial.design.simpleFactory;

/**
 * Created by faker on 2016/5/10.
 */
public class OperatorFactory {

    public static Operator createOperator(String operate) {
        Operator operator;
        switch (operate) {
            case "+":
                operator = new OperatorAdd();
                break;
            case "-":
                operator = new OperatorSub();
                break;
            case "*":
                operator = new OperatorMul();
                break;
            case "/":
                operator = new OperatorDiv();
                break;
            default:
                operator = new OperatorAdd();
        }

        return operator;
    }
}
