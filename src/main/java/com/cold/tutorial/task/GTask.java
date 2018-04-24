package com.cold.tutorial.task;

/**
 * @author liaowenhui
 * @date 2016/5/25 13:58.
 */
public class GTask implements Runnable{
    private String taskId;
    private String trigger;

    public String getTaskId() {
        return taskId;
    }


    public String getTrigger() {
        return trigger;
    }

    @Override
    public void run() {

    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }
}

