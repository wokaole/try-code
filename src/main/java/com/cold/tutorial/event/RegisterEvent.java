package com.cold.tutorial.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by hui.liao on 2015/11/5.
 */
public class RegisterEvent extends ApplicationEvent{
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public RegisterEvent(Object source) {
        super(source);
    }
}
