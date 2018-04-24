package com.cold.tutorial.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 15-6-28.
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String message = getMessage(args);
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println("[x] send " + message);
        channel.close();
        connection.close();
    }

    private static String getMessage(String[] args) {
        if (args.length == 0) {
            return "hello world!!!";
        }
        return joinString(args, " ");
    }

    private static String joinString(String[] args, String delimiter) {
        if (args.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(args[0]);
        for (int i = 1; i < args.length; i++) {
            sb.append(delimiter + args[i]);
        }
        return sb.toString();
    }
}
