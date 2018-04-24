package com.cold.tutorial.proxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author hui.liao
 *         2016/3/23 17:02
 */
public class CglibProxy implements MethodInterceptor {


    //object 委托类
    public  <T> T getInstance(Class<T> cls) {
        //不同于JDK的动态代理，它不能在创建代理时传obj对 象，obj对象必须被CGLIB包来创建
        Enhancer enhancer = new Enhancer();  //增强类
        enhancer.setSuperclass(cls); //设置被代理类字节码（obj将被代理类设置成父类；作为产生的代理的父类传进来的），CGLIB根据字节码生成被代理类的子类
        enhancer.setCallback(this); //设置回调函数，即一个方法拦截
        return (T)enhancer.create();  //创建代理类
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("obj:" + obj.getClass().getName());  //由CGLib动态生成的代理类实例
        System.out.println("method:" + method.getName());   //上文中实体类所调用的被代理的方法引用
        System.out.println("methodProxy:" + proxy.getClass().getName()); //生成的代理类对方法的代理引用
        System.out.println("args:"+ args); //参数值列表

        Object object = proxy.invokeSuper(obj, args);  //调用代理类实例上的methodProxy方法的父类方法（即实体类RealSubject中的对应方法）
        return object;
    }

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        SampleClass instance = proxy.getInstance(SampleClass.class);
        System.out.println(instance.test("CGLibProxy"));

//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(SampleClass.class);
//        enhancer.setCallback(new FixedValue() {
//            @Override
//            public Object loadObject() throws Exception {
//                return "Hello cglib!";
//            }
//        });
//        SampleClass proxy = (SampleClass) enhancer.create();
//        System.out.println(proxy.CGLibProxy(null));
    }
}
