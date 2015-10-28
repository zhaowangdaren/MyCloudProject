package cn.edu.ustc.zy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 该类主要是用来维护client端与broker的连接
 * @author ustc-zy
 *
 */
public class CloudTestService extends TimerTask{

	private final String queueName;
	private String strHostUrl="localhost";
	private int iHostPortNum = 61616;
	
	//一个连接存活1分钟
	private short sKeepAlive = 60 * 1;
	
	private long lKeepAliveInterval = 1000 * 60 * 28;
	
	
	
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	
	
	
	private RestartCallbackInterface restartInterface;
	public CloudTestService(String url, int port, String queueName, RestartCallbackInterface restartInterface){
		this.setStrHostUrl(url);
		this.setiHostPortNum(port);
		this.queueName = queueName;
		this.restartInterface = restartInterface;
		
		String urlAll = "tcp://"+strHostUrl+":"+iHostPortNum;
		connectionFactory = new ActiveMQConnectionFactory(urlAll);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(connect()){
			startAction();
		}
	}
	
	private void startAction() {
		// TODO Auto-generated method stub
		System.out.println("StartAction-keepAlive:"+sKeepAlive);
		try {
			Session session = connection.createSession(false, 
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageConsumer consumer = session.createConsumer(destination);
			while(true){
//				MapMessage message = (MapMessage)consumer.receive(sKeepAlive * 1000);
				MapMessage message = (MapMessage)consumer.receive(sKeepAlive );
				if(null != message){
					byte testType = message.getByte(TestFactory.TEST_TYPE);
					String command = message.getString(TestFactory.COMMAND_CONTENT);
					switch(testType){
					case TestFactory.CPU_TEST:
						TestFactory.cpuTest(command);
						break;
					case TestFactory.MEMORY_TEST:
						TestFactory.memoryTest(command);
						break;
					case TestFactory.NET_TEST:
						TestFactory.netTest(command);
						break;
					case TestFactory.FILE_IO_TEST:
						TestFactory.fileIoTest(command);
						break;
					}
				}else{
					break;
				}
			}
			
			if(consumer != null)consumer.close();
			if(session != null) session.close();
			if(connection != null) connection.close();
			
			//restart  this service
			this.restartInterface.restartService();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean connect(){
		System.out.println("Connect...");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			return true;
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			this.restartInterface.restartService();
		}
		
		return false;
	}
	
	
	

	public String getStrHostUrl() {
		return strHostUrl;
	}

	public void setStrHostUrl(String strHostUrl) {
		this.strHostUrl = strHostUrl;
	}

	public int getiHostPortNum() {
		return iHostPortNum;
	}

	public void setiHostPortNum(int iHostPortNum) {
		this.iHostPortNum = iHostPortNum;
	}

	public short getsKeepAlive() {
		return sKeepAlive;
	}

	public void setsKeepAlive(short sKeepAlive) {
		this.sKeepAlive = sKeepAlive;
	}

	public long getlKeepAliveInterval() {
		return lKeepAliveInterval;
	}

	public void setlKeepAliveInterval(long lKeepAliveInterval) {
		this.lKeepAliveInterval = lKeepAliveInterval;
	}
	
}
