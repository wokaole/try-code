package com.cold.tutorial.design.chain;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author hui.liao
 *         2015/12/17 19:37
 */
public class XssFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        System.out.println("XssFilter request");
        filterChain.doFilter(request, response);
        System.out.println("XssFilter response");
    }
}
