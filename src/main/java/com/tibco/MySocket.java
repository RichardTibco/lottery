package com.tibco;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @˵�� �����¶�������Ӷ�������������������ԣ���д�˹رյķ���
 * @author yangping
 * @version 1.0
 * @since
 */
public class MySocket extends Socket { //implements Runnable {
	/**
	 * Ϊ����������������
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
	 * ���ǹرյķ���
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
//				logger.error("�������ʱ�쳣������Դ���ƣ�" + name, e);
//				// e.printStackTrace();
//			}
//			
//			// ִ�м��
//			try {
//				logger.error("���μ�������");
//				Thread.sleep(5000);
//			} catch (Exception e) {
//			}
//			
//		}
//
//	}
}