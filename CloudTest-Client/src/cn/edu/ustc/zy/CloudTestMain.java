package cn.edu.ustc.zy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CloudTestMain {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		InputStream in = CloudTestMain.class
				.getClassLoader().getResourceAsStream("./config.properties");
		
		try {
			props.load(in);
			in.close();
			
			//get host ip
			Constant.HOST_URL = props.getProperty("ip", "localhost");
			
			//get queue name
			Constant.QUEUE_NAME = props.getProperty("queue_name", "cloud_test");
			
			//get cpuburn path
			String cpuburn_path = props.getProperty("cpuburn_path", null);
			Constant.CPUBURN_PATH = cpuburn_path;
			
			//get memtester path
			Constant.MEMTESTER_PATH = 
					props.getProperty("memtester_path", null);
			//get iozone path
			Constant.IOZONE_PATH = 
					props.getProperty("iozone_path", null);
			
			//get iperf path
			Constant.IPERF_PATH = 
					props.getProperty("iperf_path", null);
			
			new ServiceHolder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
