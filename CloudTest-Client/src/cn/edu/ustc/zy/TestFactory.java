package cn.edu.ustc.zy;

import java.io.File;
import java.io.IOException;

public class TestFactory {

	public final static String TEST_TYPE="TYPE";
	public final static String COMMAND_CONTENT="CONTENT";
	public final static byte CPU_TEST = 1;
	public final static byte NET_TEST = 2;
	public final static byte MEMORY_TEST = 3;
	public final static byte FILE_IO_TEST = 4;
	
	
	/**
	 *  使用CPU burn工具进行测试
	 * @param seconds   测试时间，单位minutes
	 */
	public static void cpuTest(int minutes, String resultFileName){
		String command = Constant.CPUBURN_PATH + File.separator+
				"./cpuburn-in " + minutes + " >> "+ resultFileName;
		Runtime runtime = Runtime.getRuntime();
		
		try {
			runtime.exec(command);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param command "minutes&resultFileName"
	 */
	public static void cpuTest(String command){
		int minutes = 1;
		String resultFileName = System.currentTimeMillis()+"";
		try{
			String[] args = command.split("&");
			minutes = Integer.valueOf(args[0]);
			resultFileName = args[1];
		}catch (Exception e){
			e.printStackTrace();
		}
		cpuTest(minutes, resultFileName);
	}
	
	/**
	 * net测试需要server端和client端
	 * @param serverURL
	 */
	public static void netTest(String serverURL){
		
	}
	
	/**
	 * 
	 * @param size 申请测试的内存大小，单位M
	 * @param times 测试次数
	 */
	public static void memoryTest(int size, int times){
		
	}
	
	/**
	 * 
	 * @param command "size&times"
	 */
	public static void memoryTest(String command) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * FILE_IO_TEST
	 * @param size 测试的文件大小
	 * @param resultFileName
	 */
	public static void fileIoTest(int size, String resultFileName){
		
	}

	/**
	 * 
	 * @param command "size&resultFileName"
	 */
	public static void fileIoTest(String command) {
		// TODO Auto-generated method stub
		
	}


	
	
}
