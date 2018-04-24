package com.cold.tutorial.design.factory;

/**
 * @author hui.liao
 *         2015/12/17 19:24
 */
public class App {
    public static void main(String[] args) {
        new TricycleFactory().getCycle().move();
        new UnicycleFactory().getCycle().move();
        new BicycleFactory().getCycle().move();
    }
}
