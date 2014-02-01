package com.tibco;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;
/**
 * @说明 轮询检测某个连接当前是否可用
 * @author yangping
 * @version 1.0
 * @since 当遇到一个错误的连接，将会启动重连，同时挂起该连接的使用
 */
public class CheckThread  implements Runnable {
	private static Log logger = LogFactory.getLog(CheckThread.class);	
	
	private ObjectPool pool = null;
	
	private List<Socket> sockets = new ArrayList<Socket>();
	
	public CheckThread(ObjectPool pool) {
		this.pool = pool;
	}

	
	public void run() {
		Socket socket = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while(true){
			
			while(pool.getNumIdle() != 0) {
				try {
					socket = (Socket) pool.borrowObject();
					logger.warn(format.format(new Date()) + " "+((MySocket)socket).getName() + " " + socket.toString());
					socket.sendUrgentData(0xFF);
					sockets.add(socket) ;
				} catch (NoSuchElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(Socket s : sockets) {
				try {
					pool.returnObject(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			sockets.clear();
			
			
//			for(SocketEntity socketEntity : SocketKeep.socketEntityList){
//				if(null != socketEntity && socketEntity.isKeepConn()){
//					String isLock = SocketKeep.socketIsLock.get(socketEntity.getName());
//					logger.warn("CheckThread " + isLock);
//					// 如果当前未被使用
//					if(!"1".equals(isLock)){
//						// 锁定引用
//						SocketKeep.socketIsLock.put(socketEntity.getName(), "1");
//						socket = SocketKeep.socketMap.get(socketEntity.getName());
//						try {
//							// 发送一个心跳包
//							socket.sendUrgentData(0xFF);
//							// 释放资源
//							SocketKeep.socketIsLock.put(socketEntity.getName(), "0");
//						} catch (Exception e) {
//							logger.error("检查连接时异常！启动重连！资源名称：" + socketEntity.getName(), e);
//							// 如果异常，应该建立一个线程去初始化该连接
//							InitSocket initS = new InitSocket(socketEntity.getName());
//							new Thread(initS).start();
//						}
//					}
//				}
//			}
			// 执行间隔
			try {
				logger.warn("本次检测结束！");
				Thread.sleep(2 * 1000);
			} catch (Exception e) {
			}	
			
			
		}
	}
}