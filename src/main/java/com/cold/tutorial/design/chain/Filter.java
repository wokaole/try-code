package com.cold.tutorial.design.chain;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author hui.liao
 *         2015/12/17 19:35
 */
public interface Filter {

    void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain);
}
