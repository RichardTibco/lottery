package com.tibco;

import java.io.IOException;
import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.PoolableObjectFactory;

import com.socket.SocketKeep;
import com.tibco.MySocket;

public class TestPoolableSocketFactory implements PoolableObjectFactory<MySocket> {
	private static Log logger = LogFactory.getLog(TestPoolableSocketFactory.class);



	@Override
	public void destroyObject(MySocket socket) throws Exception {
		socket.close();

	}

	@Override
	public MySocket makeObject() throws Exception {
		MySocket socket = null;
		try {
//			Class cls = Class.forName("com.tibco.MySocket");
//			Class[] types = new Class[] { String.class, Integer.class }; 
//			Constructor cons = cls.getConstructor(types); 
//			Object[] args = new Object[] { "127.0.0.1", 8001 }; 
//			socket = (MySocket) cons.newInstance(args);
			socket = new MySocket("127.0.0.1", 8001);
			
			socket.setSoTimeout(0);
			socket.setKeepAlive(true);
			socket.setName("test socket");
		} catch (Exception e) {
			logger.error("初始化某个连接时错误！错误的连接将放弃！资源名称：" + "", e);
			socket = null;
		}
		
		if(null != socket){
//			new Thread(socket).start();
			return socket;
		}else{
			return  new MySocket();					
		}
	}
	
	@Override
	public void activateObject(MySocket socket) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void passivateObject(MySocket socket) throws Exception {

	}

	@Override
	public boolean validateObject(MySocket socket) {
		try {
			socket.sendUrgentData(0xFF);
		} catch (IOException e) {
			logger.warn("初始化资源执行结束！资源名称：" + "test socket");
//			e.printStackTrace();
			return false;
		}
		return true;
	}


}
