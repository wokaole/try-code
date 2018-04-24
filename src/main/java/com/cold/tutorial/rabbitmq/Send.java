package com.cold.tutorial.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * @author hui.liao
 *         2016/1/29 14:54
 */
public class Send {

    //private static final String QUEUE_NAME = "HELLO";
    private static final String TASK_QUEUE_NAME = "TASK_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        channel.basicQos(1);
        String msg = "hello world..";
        IntStream.range(0, 10).forEach(i -> {
            try {
                String s = msg + "_" + i;
                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, s.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println(" [x] Sent '" + msg + "'");

        channel.close();
        connection.close();


    }

}
