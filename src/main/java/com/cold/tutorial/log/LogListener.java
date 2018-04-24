package com.cold.tutorial.log;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.util.ByteSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.concurrent.TimeUnit;
/**
 * Created by faker on 2016/7/17.
 */
@Component
public class LogListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(LogListener.class);

    @Override
    public void onMessage(Message message) {
        ByteSequence content = ((ActiveMQObjectMessage) message).getContent();
//            System.out.println("Received log [" + event.getLevel() + "]: "+ event.getMessage());
        System.out.println(content);
    }

    public static void main(String[] args) {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();

//            Topic topic = session.createTopic("logTopic");
            Queue queue = session.createQueue("logQueue");
            MessageConsumer consumer = session.createConsumer(queue);
//            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(new LogListener());

            log.info("info log");
            log.warn("warn log");
            log.error("error log");

            TimeUnit.SECONDS.sleep(5);
            consumer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
