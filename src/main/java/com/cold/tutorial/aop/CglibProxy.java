package com.cold.tutorial.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liaowenhui
 * @date 2017/2/22 19:53.
 */
public class CglibProxy implements MethodInterceptor{

    public <T> T getInstance(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("obj:" + o.getClass().getName());  //由CGLib动态生成的代理类实例
        System.out.println("method:" + method.getName());   //上文中实体类所调用的被代理的方法引用
        System.out.println("methodProxy:" + methodProxy.getClass().getName()); //生成的代理类对方法的代理引用
        System.out.println("args:"+ objects); //参数值列表
        return methodProxy.invokeSuper(o ,objects);
    }

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        UserUtils instance = cglibProxy.getInstance(UserUtils.class);
        instance.add("ttt");
    }
}
