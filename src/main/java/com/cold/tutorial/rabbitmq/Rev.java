package com.cold.tutorial.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author hui.liao
 *         2016/1/29 15:13
 */
public class Rev {

    //private static final String QUEUE_NAME = "HELLO";
    private static final String TASK_QUEUE_NAME = "TASK_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        channel.basicConsume(TASK_QUEUE_NAME, false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + msg + "'");

                try{
                    doWork(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });


    }

    private static void doWork(String msg) throws InterruptedException {
        for(char ch: msg.toCharArray()) {
            if (ch == '.') {
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}
