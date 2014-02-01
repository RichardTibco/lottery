package com.tibco.test;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

import com.tibco.CheckThread;
import com.tibco.MySocket;
import com.tibco.TestPoolableSocketFactory;

public class TestPool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PoolableObjectFactory factory = new TestPoolableSocketFactory();

//		GenericObjectPool pool= new GenericObjectPool(factory, 10, GenericObjectPool.WHEN_EXHAUSTED_BLOCK, 100, true, true);
		ObjectPool pool = new GenericObjectPool(factory,3);
//		ObjectPool pool = new StackObjectPool(factory);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
//		new Thread(new CheckThread(pool)).start();
		while(true) {
			try {
				Socket socket1 = null;
				
				socket1 = (Socket) pool.borrowObject();
				
				if(null != socket1){
					System.out.println(format.format(new Date()) + " "+((MySocket)socket1).getName() + " " + socket1.toString());
				}
				socket1.sendUrgentData(0xFF);
				
				Socket socket2 = null;
				
				socket2 = (Socket) pool.borrowObject();
				
				if(null != socket2){
					System.out.println(format.format(new Date()) + " "+((MySocket)socket2).getName() + " " + socket2.toString());
				}
				socket2.sendUrgentData(0xFF);
				
				
				
				pool.returnObject(socket1);
				pool.returnObject(socket2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				
			}
			
		}
	}

}
