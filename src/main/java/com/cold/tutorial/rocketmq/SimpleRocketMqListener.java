package com.cold.tutorial.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.cold.tutorial.mybatis.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 业务监听实现Demo
 * Created by wangxingzhe on 2015/11/3.
 */
public class SimpleRocketMqListener implements RocketMqMessageListener {

    @Autowired
    public DemoDao demoDao;

    public boolean onMessage(List<MessageExt> messages, ConsumeConcurrentlyContext Context) {
        for (int i = 0; i < messages.size(); i++) {
            Message msg = messages.get(i);
            System.out.println(msg.toString());
            demoDao.insert(msg.getTopic(), msg.getTags(), msg.getKeys());
        }
        return true;
    }
}
