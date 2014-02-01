package com.socket;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * @说明 被重新定义的连接对象，增加了名字这个属性，重写了关闭的方法
 * @author yangping
 * @version 1.0
 * @since
 */
public class MySocket extends Socket{	
	/**
	 * 为对象增加名称属性
	 */
	private String name;	
	public MySocket() {
	}	
	public MySocket(String ip,int port) throws UnknownHostException, IOException{
		super(ip, port);
	}	
	/**
	 * 覆盖关闭的方法
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