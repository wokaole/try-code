package com.cold.tutorial.design.chain;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author hui.liao
 *         2015/12/17 19:37
 */
public class LoginFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        System.out.println("LoginFilter request1");
        filterChain.doFilter(request, response);
        System.out.println("LoginFilter response");
    }
}
