package com.cold.tutorial.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hui.liao
 *         2016/3/23 16:53
 */
public class DynamicProxy implements InvocationHandler{

    private Object object; //委托类，真实类

    private DynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(object, args); //利用反射来调用方法
    }

    public Object buildProxy(Object object) {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
        DynamicProxy proxy = new DynamicProxy(calculator);
        Calculator c1 = (Calculator) proxy.buildProxy(calculator);
        System.out.println(c1.add(3, 5));
    }
}
