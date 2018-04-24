package com.cold.tutorial.jms;


import javax.jms.*;
import javax.naming.InitialContext;

public class Chat implements MessageListener{

	private TopicSession pubSession;  
    private TopicPublisher publisher;  
    private TopicConnection conntection;  
    private String username; 
    
    public Chat(String topicFactory,String topicName,String username) throws Exception{
    	// JNDI ConnectionFactory
    	InitialContext ctx = new InitialContext();
    	TopicConnectionFactory conFactory = (TopicConnectionFactory)ctx.lookup(topicFactory);

    	// ConnectionFactory Connection
        TopicConnection connection = conFactory.createTopicConnection();

        // session
        TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        // JNDI Topic
        Topic chatTopic = (Topic)ctx.lookup(topicName);

        // session Topic
        TopicPublisher publisher = pubSession.createPublisher(chatTopic);
        TopicSubscriber subscriber = subSession.createSubscriber(chatTopic);

        subscriber.setMessageListener(this);
        this.conntection = connection;
        this.pubSession = pubSession;
        this.publisher = publisher;
        this.username = username;

        conntection.start();
    }

	@Override
	public void onMessage(Message m) {
        try {
        	TextMessage tm = (TextMessage)m;
			System.out.println(tm.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	protected void writeMessage(String text) {
		 try {
			TextMessage message = pubSession.createTextMessage();
			message.setText(username+": "+text);
			//
	        publisher.publish(message);
		} catch (JMSException e) {
			e.printStackTrace();
		} 
	}
	
	public void close() throws Exception{  
        conntection.close();  
    }  
	
//	public static void main(String[] args) {
//		try {
//			Chat chat = new Chat("TopicCF","topic1","hh");
//			BufferedReader commandLine = new BufferedReader(new InputStreamReader(System.in));
//			
//			while(true) {
//				String s = commandLine.readLine();
//				if ("exit".equalsIgnoreCase(s)) {
//					chat.close();
//					System.exit(0);
//				} else {
//					chat.writeMessage(s);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
}
