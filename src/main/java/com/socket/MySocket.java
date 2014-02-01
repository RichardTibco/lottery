package com.socket;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * @˵�� �����¶�������Ӷ�������������������ԣ���д�˹رյķ���
 * @author yangping
 * @version 1.0
 * @since
 */
public class MySocket extends Socket{	
	/**
	 * Ϊ����������������
	 */
	private String name;	
	public MySocket() {
	}	
	public MySocket(String ip,int port) throws UnknownHostException, IOException{
		super(ip, port);
	}	
	/**
	 * ���ǹرյķ���
	 */
	@Override
	public synchronized void close() throws IOException {
		SocketKeep.socketIsLock.put(this.name, "0");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}