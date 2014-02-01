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
 * @˵�� ��ѯ���ĳ�����ӵ�ǰ�Ƿ����
 * @author yangping
 * @version 1.0
 * @since ������һ����������ӣ���������������ͬʱ��������ӵ�ʹ��
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
//					// �����ǰδ��ʹ��
//					if(!"1".equals(isLock)){
//						// ��������
//						SocketKeep.socketIsLock.put(socketEntity.getName(), "1");
//						socket = SocketKeep.socketMap.get(socketEntity.getName());
//						try {
//							// ����һ��������
//							socket.sendUrgentData(0xFF);
//							// �ͷ���Դ
//							SocketKeep.socketIsLock.put(socketEntity.getName(), "0");
//						} catch (Exception e) {
//							logger.error("�������ʱ�쳣��������������Դ���ƣ�" + socketEntity.getName(), e);
//							// ����쳣��Ӧ�ý���һ���߳�ȥ��ʼ��������
//							InitSocket initS = new InitSocket(socketEntity.getName());
//							new Thread(initS).start();
//						}
//					}
//				}
//			}
			// ִ�м��
			try {
				logger.warn("���μ�������");
				Thread.sleep(2 * 1000);
			} catch (Exception e) {
			}	
			
			
		}
	}
}