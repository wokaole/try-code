package com.cold.tutorial.log;

import java.io.Serializable;

/**
 * @author liaowenhui
 * @date 2016/7/21 15:50.
 */
public class MessageInfo implements Serializable{

    private long messageId;
    private long messageType;
    private String title;
    private String content;
    private String receivers;
    private String logMessage;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getMessageType() {
        return messageType;
    }

    public void setMessageType(long messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
}
