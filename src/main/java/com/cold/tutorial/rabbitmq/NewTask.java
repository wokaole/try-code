package com.cold.tutorial.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hui.liao
 *         2016/1/29 15:48
 */
public class NewTask {
    private static final String QUEUE_NAME = "HELLO";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "hello...";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        channel.close();
        connection.close();
    }
}
