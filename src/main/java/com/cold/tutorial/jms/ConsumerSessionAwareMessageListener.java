package com.cold.tutorial.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.*;

public class ConsumerSessionAwareMessageListener implements
		SessionAwareMessageListener<TextMessage> {
	
	@Autowired
	@Qualifier("queueDestination")
	private Destination destination; 
	
	@Override
	public void onMessage(TextMessage message, Session session)
			throws JMSException {
		System.out.println("收到一条消息...");
		System.out.println("消息内容是：" + message.getText());
		MessageProducer producer = session.createProducer(destination);
		Message textMessage = session.createTextMessage("ConsumerSessionAwareMessageListener。。。");
		producer.send(textMessage);
	}

}
