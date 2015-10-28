import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ReceiveTopic implements Runnable{

	private String threadName;
	
	public ReceiveTopic(String threadName){
		this.threadName = threadName;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReceiveTopic receiver1 = new ReceiveTopic("1");
		Thread thread1 = new Thread(receiver1);
		thread1.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ConnectionFactory connectionFactory;
		Connection connection = null;
		
		Session session;
		
		Destination destination;
		
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://192.168.2.155:61616");
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic("test-topic");
			consumer = session.createConsumer(destination);
			while(true){
				TextMessage message = (TextMessage)consumer.receive(100*1000);
				if(null != message){
					System.out.println("Thread:"+threadName+"receiver message--"+message.getText());
				}
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
