package com.cold.tutorial.jms;

import javax.jms.Destination;


public interface ProducerService {
	
	public void sendMessage(Destination destination, final String message);
}
