package com.cold.tutorial.design.factory;

/**
 * Created by faker on 2015/9/13.
 */
public class BicycleCycle implements  Cycle{
    @Override
    public void move() {
        System.out.println("BicycleCycle move!!!");
    }
}
