import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Main {
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
//			receiverTopic2("test-topic");
			String url="tcp://192.168.2.155:61616";
			receiverTopic(url,"test-topic");
//			new Thread(new Runnable(){
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					try {
//						receiverTopic(url,"test-topic");
//					} catch (JMSException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				
//			}).start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	public static void receiverQueue(String queueName) throws JMSException{
		String url="tcp://localhost:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(queueName);
		MessageConsumer consumer = session.createConsumer(destination);
		
		System.out.println("Consumer:->Begin Receive...");
		while(true){
			TextMessage message = (TextMessage)consumer.receive(1000);
			if(null != message){
				System.out.println("Consumer:->Receiving message: "+ message.getText());
			}else{
				System.out.println("Consumer:->Receive completed");
				break;
			}
		}
		
		if(consumer != null){
			consumer.close();
		}
		if(session != null)
			session.close();
		
		if(connection != null)
			connection.close();
		
		System.out.println("Consumer:->Closing connection");
		
	}
	
	public static void receiverTopic(String url, String topicName) throws JMSException{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin",url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		System.out.println("Begin。。。");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic(topicName);
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new MessageListener(){
			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				TextMessage msg = null;
				
				if(message instanceof TextMessage){
					msg = (TextMessage)message;
					try {
						System.out.println("Consumer:->Receiving message: "+ msg.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					System.out.println("Message of wrong type: "+message.getClass().getName());
				}
			}
		});
		
		
	}
	
	public static void receiverTopic2(String topicName) throws JMSException{
		String url="tcp://localhost:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic(topicName);
		MessageConsumer consumer = session.createConsumer(destination);
		
		System.out.println("Consumer:->Begin Receive。。。");
		while(true){
			TextMessage message = (TextMessage)consumer.receive(1000);
			if(null != message){
				System.out.println("Consumer:->Receiving message: "+ message.getText());
			}else{
				System.out.println("Consumer:->Receive completed");
				break;
			}
		}
		
		if(consumer != null){
			consumer.close();
		}
		if(session != null)
			session.close();
		
		if(connection != null)
			connection.close();
		
		System.out.println("Consumer:->Closing connection");
		
	}

	private static class TextListener implements MessageListener{
		@Override
		public void onMessage(Message message) {
			// TODO Auto-generated method stub
			TextMessage msg = null;
			
			if(message instanceof TextMessage){
				msg = (TextMessage)message;
				try {
					System.out.println("Consumer:->Receiving message: "+ msg.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				System.out.println("Message of wrong type: "+message.getClass().getName());
			}
		}
	}
	
	
	

}
