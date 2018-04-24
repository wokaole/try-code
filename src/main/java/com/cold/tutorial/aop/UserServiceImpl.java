package com.cold.tutorial.aop;

/**
 * @author liaowenhui
 * @date 2017/2/22 19:45.
 */
public class UserServiceImpl implements UserService {
    @Override
    public int add(String name) {
        System.out.println("add user :" + name);
        return 1;
    }
}
