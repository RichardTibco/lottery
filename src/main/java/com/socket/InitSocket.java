package com.socket;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @˵�� �����ʼ��ʧЧ������
 * @author yangping
 * @version 1.0
 * @since
 */
public class InitSocket implements Runnable{	
	private static Log logger = LogFactory.getLog(InitSocket.class);	
	/**
	 * �Ƿ���ĳ�����ӵ�������Ϣ��ֻ����������Ϣ���ܽ�������
	 */
	private static boolean isHave = false;	
	private SocketEntity socketEntity = null;	
	private String name;	
	public InitSocket(String name){
		this.name = name;
		// ����Ƿ���ĳ�����ӵ�������Ϣ
		for(SocketEntity socketEntity : SocketKeep.socketEntityList){
			if(null != socketEntity && socketEntity.isKeepConn()){
				if(socketEntity.getName().equals(name)){
					this.setSocketEntity(socketEntity);
					isHave = true;
				}
			}
		}
	}
	public void run() {
		boolean isError = true;
		MySocket socket = null;
		if(isHave){
			while(isError){
				try {
					socket = new MySocket(this.getSocketEntity().getIp(),this.getSocketEntity().getPort());
					socket.setSoTimeout(0);
					socket.setKeepAlive(true);
					socket.setName(this.name);
					// ����һ��������
					socket.sendUrgentData(0xFF);						
				} catch (Exception e) {
					logger.error("������Դ����ʱ������Դ��" + this.name, e);
//					socket = null;
					try {
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(null != socket){
					SocketKeep.socketMap.put(this.getSocketEntity().getName(), socket);
					// �������ӵ�ǰ����
					logger.warn("������Դ���ӳɹ�����Դ���ƣ�" + this.name);
					isError = false;
				}
				try {
					Thread.sleep(2 * 1000);
				} catch (Exception e) {
				}
				
			}
		}else{
			logger.error("û�з���ָ����Դ��������Ϣ����Դ���ƣ�" + this.name);
		}
		logger.warn("��ʼ����Դִ�н�������Դ���ƣ�" + this.name);
	}
	public SocketEntity getSocketEntity() {
		return socketEntity;
	}
	public void setSocketEntity(SocketEntity socketEntity) {
		this.socketEntity = socketEntity;
	}
}