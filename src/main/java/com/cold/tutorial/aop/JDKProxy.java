package com.cold.tutorial.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liaowenhui
 * @date 2017/2/22 19:39.
 */
public class JDKProxy implements InvocationHandler{

    private Object proxy;

    public JDKProxy(Object proxy) {
        this.proxy = proxy;
    }

    public Object buildProxy() {
        return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        System.out.println("proxy object!!!");
        return method.invoke(proxy, args);
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        JDKProxy proxy = new JDKProxy(userService);
        UserService o = (UserService)proxy.buildProxy();
        System.out.println(o.add("ttt"));
    }
}
