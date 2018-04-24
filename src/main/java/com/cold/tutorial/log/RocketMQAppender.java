package com.cold.tutorial.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liaowenhui
 * @date 2016/7/21 16:05.
 */
public class RocketMQAppender extends AppenderSkeleton {

    public static final String CATEGORY_LEVEL = "level";
    /**
     * log event 发送队列
     */
    private final LinkedBlockingQueue<Event> events = new LinkedBlockingQueue<Event>();
    /**
     * 发送线程池
     */
    private ExecutorService senderPool = null;
    /**
     * 多少个线程同时发送
     */
    private int senderPoolSize = 2;
    /**
     * 失败重试次数
     */
    private int maxSenderRetries = 3;
    private final Timer retryTimer = new Timer("log-event-retry", true);
    /**
     *客户端限制的消息大小
     */
    private int maxMessageSize = 1024 * 128;
    /**
     * RocketMQ name server
     */
    private String namesrvAddr = "localhost:9876";
    /**
     * topic 名称
     */
    private String topicName = "rocketMQLogTopic";
    /**
     * tags 一般用来标识消息子类型
     */
    private String tags = "logs";
    /**
     * 每个消息在业务层面的唯一标识码, 可以通过 topic，key 来查询这条消息内容,务必保证 key 尽可能唯一
     * 可以用消息ID来设置
     */
    private String keys = "123";

    private String timeZone = "UTC";
    private String timeFormat = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat formatter;
    /**
     * Producer 组名
     */
    private String producerGroup = "rocketMQLogProducer";
    private DefaultMQProducer producer;

    @Override
    public void activateOptions() {
        try {
            this.producer = new DefaultMQProducer(this.producerGroup);
            this.producer.setNamesrvAddr(this.namesrvAddr);
            this.producer.setMaxMessageSize(this.maxMessageSize);
            this.formatter = new SimpleDateFormat(this.timeFormat);
            this.formatter.setTimeZone(TimeZone.getTimeZone(this.timeZone));
            startSenders();
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    protected void startSenders() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("rocketMq-log-pool-%d").setDaemon(true).build();
        this.senderPool = Executors.newFixedThreadPool(this.senderPoolSize, threadFactory);
        for(int i=0; i<senderPoolSize; i++) {
            this.senderPool.submit(new EventSender());
        }
    }

    @Override
    protected void append(LoggingEvent event) {
        this.events.add(new Event(event));
    }

    @Override
    public void close() {
        if (this.senderPool != null) {
            this.senderPool.shutdownNow();
            this.senderPool = null;
        }

        if (this.producer != null) {
            producer.shutdown();
        }
        this.retryTimer.cancel();
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public int getSenderPoolSize() {
        return senderPoolSize;
    }

    public RocketMQAppender setSenderPoolSize(int senderPoolSize) {
        this.senderPoolSize = senderPoolSize;
        return this;
    }

    public int getMaxSenderRetries() {
        return maxSenderRetries;
    }

    public RocketMQAppender setMaxSenderRetries(int maxSenderRetries) {
        this.maxSenderRetries = maxSenderRetries;
        return this;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public RocketMQAppender setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
        return this;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public RocketMQAppender setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
        return this;
    }

    public String getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
        this.formatter = new SimpleDateFormat(timeFormat);
        this.formatter.setTimeZone(TimeZone.getTimeZone(this.timeZone));
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        this.formatter = new SimpleDateFormat(this.timeFormat);
        this.formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    protected class EventSender implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    final Event event = RocketMQAppender.this.events.take();
                    LoggingEvent logEvent = event.getEvent();
                    try {
                        String time = RocketMQAppender.this.formatter.format(new Date(logEvent.getTimeStamp()));
                        String level = logEvent.getLevel().toString();
                        String location = logEvent.getLocationInformation().fullInfo.toString();
                        String message = logEvent.getMessage().toString();
                        String threadName = logEvent.getThreadName();

                        LogItem item = new LogItem();
                        MessageInfo msg = JSON.parseObject(message, MessageInfo.class);
                        item.setTime(time).setLevel(level).setLocation(location).setMessage(msg).setThreadName(threadName);

                        Message message1 = new Message(topicName, tags, msg.getMessageId() + "", JSON.toJSONString(item).getBytes());
                        RocketMQAppender.this.producer.sendOneway(message1);

                    } catch (RemotingException e) {
                        retrySend(event, logEvent, e);
                    } catch (MQClientException e) {
                        retrySend(event, logEvent, e);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

        /**
         * 发送失败时重试，重试次数越多，延时越多, 延时时间为 n^n
         * @param event
         * @param logEvent
         * @param e
         */
        private void retrySend(final Event event, LoggingEvent logEvent, Exception e) {
            int retries = event.incrementRetries();
            if (retries < RocketMQAppender.this.maxSenderRetries) {
                RocketMQAppender.this.retryTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        RocketMQAppender.this.events.add(event);
                    }
                }, (long) Math.pow(retries, Math.log(retries)) * 1000);
            } else {
                errorHandler.error("Could not send log message " + logEvent.getRenderedMessage() + " after "
                                + RocketMQAppender.this.maxSenderRetries + " retries",
                        e, ErrorCode.WRITE_FAILURE, logEvent);
            }
        }
    }

    protected static class Event {

        private final LoggingEvent event;


        private final AtomicInteger retries = new AtomicInteger(0);

        public Event(LoggingEvent event) {
            this.event = event;
        }

        public LoggingEvent getEvent() {
            return this.event;
        }

        public int incrementRetries() {
            return this.retries.incrementAndGet();
        }

    }
}
