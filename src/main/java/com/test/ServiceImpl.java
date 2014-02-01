package com.test;
import java.net.Socket;
/**
 * @˵�� ѭ��������������������
 * @author yangping
 * @version 1.0
 * @since 
 */
public class ServiceImpl implements Runnable {
	Socket socket = null;
	public ServiceImpl(Socket s) {
		this.socket = s;
	}
	public void run() {
		boolean isKeep = true;
		try {
			while (isKeep) {
//				socket.sendUrgentData(0xFF);
				System.out.println("Server " + socket.toString());
				Thread.sleep(5 * 1000);
			}
		} catch (Exception e) {
			isKeep = false;
		}
	}
}