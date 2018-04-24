package com.cold.tutorial.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * @author liaowenhui
 * @date 2016/7/19 14:10.
 */
public class MQProducer {
    private String namesrvAddr;
    private String producerGroup;
    private DefaultMQProducer producer;

    public void init() {
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        try {
            producer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
    }

    public SendResult send(Message message) throws
            InterruptedException, RemotingException, MQClientException, MQBrokerException {
        SendResult result = producer.send(message);
        return result;
    }

    public void destory() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public MQProducer setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
        return this;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public MQProducer setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
        return this;
    }
}
