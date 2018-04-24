package com.cold.tutorial.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liaowenhui
 * @date 2016/7/19 14:19.
 */
public class MQConsumer {

        private Logger logger= LoggerFactory.getLogger(getClass());
        private String topic;
        private String subExpression;
        private String consumerGroup;
        private String namesrvAddr;
        private RocketMqMessageListener rocketMqMessageListener;
        private DefaultMQPushConsumer consumer;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getSubExpression() {
            return subExpression;
        }

        public void setSubExpression(String subExpression) {
            this.subExpression = subExpression;
        }

        public String getConsumerGroup() {
            return consumerGroup;
        }

        public void setConsumerGroup(String consumerGroup) {
            this.consumerGroup = consumerGroup;
        }

        public String getNamesrvAddr() {
            return namesrvAddr;
        }

        public void setNamesrvAddr(String namesrvAddr) {
            this.namesrvAddr = namesrvAddr;
        }

        public RocketMqMessageListener getRocketMqMessageListener() {
            return rocketMqMessageListener;
        }

        public void setRocketMqMessageListener(RocketMqMessageListener rocketMqMessageListener) {
            this.rocketMqMessageListener = rocketMqMessageListener;
        }

        public void init(){
            logger.debug("启动RocketMq监听...{}",this);
            consumer = new DefaultMQPushConsumer();
            consumer.setConsumerGroup(consumerGroup);
            consumer.setNamesrvAddr(namesrvAddr);
            try {
                //订阅PushTopic下Tag为push的消息
                consumer.subscribe(topic, subExpression);
                //程序第一次启动从消息队列头取数据
                consumer.setConsumeFromWhere(
                        ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
                RocketMqMessageWrapper rocketMqMessageWrapper=new RocketMqMessageWrapper();
                if(this.rocketMqMessageListener==null){
                    throw new RuntimeException("please define a rocketMqMessageListener for consumer!");
                }
                rocketMqMessageWrapper.setRocketMqMessageListener(this.rocketMqMessageListener);
                consumer.registerMessageListener(rocketMqMessageWrapper);
                consumer.start();
                logger.debug("启动RocketMq监听成功！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void destory() {
            if (consumer != null){
                consumer.shutdown();
            }
        }

}
