package com.cold.tutorial.log;

import java.io.Serializable;

public class LogItem implements Serializable{

    private String time;
    private String level;
    private String location;
    private MessageInfo message;
    private String threadName;

    public String getTime() {
        return time;
    }

    public LogItem setTime(String time) {
        this.time = time;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public LogItem setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public LogItem setLocation(String location) {
        this.location = location;
        return this;
    }

    public MessageInfo getMessage() {
        return message;
    }

    public LogItem setMessage(MessageInfo message) {
        this.message = message;
        return this;
    }

    public String getThreadName() {
        return threadName;
    }

    public LogItem setThreadName(String threadName) {
        this.threadName = threadName;
        return this;
    }
}
