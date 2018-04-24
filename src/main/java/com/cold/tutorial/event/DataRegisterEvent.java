package com.cold.tutorial.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by hui.liao on 2015/11/5.
 */
public class DataRegisterEvent extends ApplicationEvent{
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public DataRegisterEvent(Object source) {
        super(source);
    }
}
