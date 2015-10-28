package cn.edu.ustc.zy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;

public class ServiceHolder implements RestartCallbackInterface{

	private static Timer retryTimer = new Timer();
	private static long lInitialRetryInterval = 1000 * 6;
	private static long lMaxRetryInterval = 1000 * 60 * 5;
	
	public ServiceHolder(){
		restartService();
	}
	
	@Override
	public void restartService() {
		// TODO Auto-generated method stub
		System.out.println("Reconnect...");
		retryTimer.purge();
		/*
		retryTimer.schedule(new CloudTestService(Constant.HOST_URL,
				61616, Constant.QUEUE_NAME, this), lInitialRetryInterval);
		*/
		
		if(isNetAvailable()){
			//Cannot restart itself
			retryTimer.schedule(new CloudTestService(Constant.HOST_URL,
					61616, Constant.QUEUE_NAME, this), lInitialRetryInterval);
		}else{
			retryTimer.schedule(new CloudTestService(Constant.HOST_URL,
					61616, Constant.QUEUE_NAME, this), lMaxRetryInterval);
		}
		
	}
	
	public boolean isNetAvailable(){
		Runtime runtime = Runtime.getRuntime();
		try{
			Process process = runtime.exec("ping -c 4 "+ Constant.HOST_URL);
			InputStream in = process.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(in);
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = bufReader.readLine()) != null){
				sb.append(line);
			}
			in.close();
			inputReader.close();
			bufReader.close();
			
			if(sb != null && sb.toString().length() > 0){
				if(sb.toString().indexOf("ttl") > 0) return true;
				else return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

}
