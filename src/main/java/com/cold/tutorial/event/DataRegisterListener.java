package com.cold.tutorial.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by hui.liao on 2015/11/5.
 */
@Component
public class DataRegisterListener implements ApplicationListener<RegisterEvent> {
    @Async
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        System.out.println(Thread.currentThread());
        System.out.println("DataRegisterListener start");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DataRegisterListener end");
    }
}
