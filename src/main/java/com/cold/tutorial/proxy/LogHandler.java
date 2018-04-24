package com.cold.tutorial.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hui.liao
 *         2016/1/26 15:41
 */
public class LogHandler implements InvocationHandler{

    private Object object;

    private LogHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.doBefore();
        Object result = method.invoke(object, args);
        this.doAfter();
        return result;
    }

    public void doBefore() {
        System.out.println("do before...");
    }

    public void doAfter() {
        System.out.println("do after...");
    }

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
        LogHandler handler = new LogHandler(calculator);
        Calculator proxy = (Calculator) Proxy.newProxyInstance(calculator.getClass().getClassLoader(), calculator.getClass().getInterfaces(), handler);
        int add = proxy.add(1, 2);
        System.out.println(add);
    }

}
