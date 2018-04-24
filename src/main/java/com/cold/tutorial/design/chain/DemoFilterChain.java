package com.cold.tutorial.design.chain;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author hui.liao
 *         2015/12/17 19:54
 */
public class DemoFilterChain implements FilterChain{

    private List<Filter> filters;

    private Iterator<Filter> iterator;

    public DemoFilterChain() {
        this.filters = new ArrayList<>();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response) {
        if (iterator == null) {
            iterator = filters.iterator();
        }

        if (iterator.hasNext()) {
            Filter filter = iterator.next();
            filter.doFilter(request, response, this);
        }
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }
}
