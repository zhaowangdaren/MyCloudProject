import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			sendTopic("tokudu.9dfadbe7d6e81311");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		String url = "tcp://localhost:61616";
		String text = "Test queue sneder";
		String queueName = "Test queue sender";
		try {
			sendQueue(url, queueName, text);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sendTopic(String topicName) throws JMSException{
		String url="tcp://localhost:61616";
		ActiveMQConnectionFactory connectionFactory  = new ActiveMQConnectionFactory(url);
		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("admin");
		
		Connection connection = (Connection) connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic(topicName);
		
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		
		String test = "Helle ActiveMQ";
		TextMessage message = session.createTextMessage(test);
		
		producer.send(message);
		System.out.println("Message is send!");
		
		session.close();
		connection.close();
	}
	
	public static void sendQueue(String url, String queueName, String text) throws JMSException{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("admin");
		
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(queueName);
		
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		
		TextMessage message = session.createTextMessage(text);
		
		producer.send(message);
		System.out.println("Queue Message is send!");
		
		session.close();
		connection.close();
	}
}
