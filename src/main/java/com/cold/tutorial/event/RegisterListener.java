package com.cold.tutorial.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by hui.liao on 2015/11/5.
 */
@Component
public class RegisterListener implements ApplicationListener<RegisterEvent> {
    @Async
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        System.out.println(Thread.currentThread());
        System.out.println("RegisterEvent start");
        System.out.println("RegisterEvent end");
    }
}
