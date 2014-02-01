package com.tibco;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @说明 被重新定义的连接对象，增加了名字这个属性，重写了关闭的方法
 * @author yangping
 * @version 1.0
 * @since
 */
public class MySocket extends Socket { //implements Runnable {
	/**
	 * 为对象增加名称属性
	 */
	private static Log logger = LogFactory.getLog(MySocket.class);
	private String name;

	public MySocket() {
	}

	public MySocket(String ip, int port) throws UnknownHostException,
			IOException {
		super(ip, port);
	}

	/**
	 * 覆盖关闭的方法
	 */
	// @Override
	// public synchronized void close() throws IOException {
	// // SocketKeep.socketIsLock.put(this.name, "0");
	// }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Override
//	public void run() {
//		while(true) {
//			try {
//				this.sendUrgentData(0xFF);
//				System.out.println("send urgent data");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				logger.error("检查连接时异常！！资源名称：" + name, e);
//				// e.printStackTrace();
//			}
//			
//			// 执行间隔
//			try {
//				logger.error("本次检测结束！");
//				Thread.sleep(5000);
//			} catch (Exception e) {
//			}
//			
//		}
//
//	}
}