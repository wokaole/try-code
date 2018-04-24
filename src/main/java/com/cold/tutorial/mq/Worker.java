package com.cold.tutorial.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 15-6-28.
 */
public class Worker {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //�Ƿ���Ҫ�־û������͡����շ�����Ҫ����
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //��ƽ�ַ���������֪RabbitMQ��Ҫͬʱ��һ������߳���һ������
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        //�Ƿ���ȷ����Ϣ�� ���checkΪfalse ����Ҫchannel.basicAck
        boolean autoCheck = false;
        channel.basicConsume(QUEUE_NAME, autoCheck, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            doWork(message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            System.out.println(" [x] Done");
        }
    }

    private static void doWork(String message) throws InterruptedException {
        for (char ch : message.toCharArray()) {
            if (ch == '.') {
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}
