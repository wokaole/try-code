package com.cold.tutorial.soa;

/**
 * @author hui.liao
 *         2016/1/18 16:37
 */
public class SayHelloServiceImpl implements SayHelloService{
    @Override
    public String sayHello(String helloArg) {
        if ("hello".equals(helloArg)) {
            return helloArg;
        }
        return "byebye";
    }
}
