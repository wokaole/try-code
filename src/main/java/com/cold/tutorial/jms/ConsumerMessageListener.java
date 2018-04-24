package com.cold.tutorial.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		
		int i = 1;
        try {
        	//这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
        	TextMessage textMsg = (TextMessage) message;  
        	System.out.println("接收到一个纯文本消息。");
			System.out.println("消息内容是：" + textMsg.getText());
			if (i == 1) {
				i++;
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
