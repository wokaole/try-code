package com.cold.tutorial.design.factory;

/**
 * Created by faker on 2015/9/13.
 */
public class Cycles {
    public static void cycle(CycleFactory factory) {
        factory.getCycle().move();
    }

    public static void main(String[] args) {
        cycle(new BicycleFactory());
        cycle(new UnicycleFactory());
        cycle(new TricycleFactory());
    }
}
