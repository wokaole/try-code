package com.cold.tutorial.design.chain;

/**
 * @author hui.liao
 *         2015/12/17 20:04
 */
public class App {
    public static void main(String[] args) {
        FilterChain filterChain = new DemoFilterChain();
        filterChain.addFilter(new LoginFilter());
        filterChain.addFilter(new XssFilter());

        filterChain.doFilter(null, null);
    }
}
