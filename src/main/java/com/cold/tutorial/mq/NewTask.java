package com.cold.tutorial.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 15-6-28.
 */
public class NewTask {
    private static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        args = new String[1];
        args[0] = "NewTask First message....";
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //�Ƿ���Ҫ�־û������͡����շ�����Ҫ���á�ͬʱbasicPublish������MessageProperties(ʵ����BasicProperties)��ֵΪPERSISTENT_TEXT_PLAIN
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        String message = getMessage(args);
        channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
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
