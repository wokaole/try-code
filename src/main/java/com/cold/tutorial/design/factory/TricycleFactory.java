package com.cold.tutorial.design.factory;

/**
 * Created by faker on 2015/9/13.
 */
public class TricycleFactory implements CycleFactory{
    @Override
    public Cycle getCycle() {
        return new TricycleCycle();
    }
}
