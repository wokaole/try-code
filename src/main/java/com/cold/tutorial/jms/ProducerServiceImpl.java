package com.cold.tutorial.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired  
    @Qualifier("responseQueue")  
    private Destination responseDestination;  
	
	@Override
	public void sendMessage(Destination destination, final String message) {
		System.out.println("---------------生产者发送消息-----------------");
        System.out.println("---------------生产者发了一个消息" + message);
        jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message);
				textMessage.setJMSReplyTo(responseDestination);
				return textMessage;
			}
		});
	}

}
