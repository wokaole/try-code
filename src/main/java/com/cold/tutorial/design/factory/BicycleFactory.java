package com.cold.tutorial.design.factory;

/**
 * Created by faker on 2015/9/13.
 */
public class BicycleFactory implements CycleFactory{

    public Cycle getCycle() {
        return new BicycleCycle();
    }
}
