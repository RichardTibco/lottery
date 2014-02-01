package com.socket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @˵�� ��ѯ���ĳ�����ӵ�ǰ�Ƿ����
 * @author yangping
 * @version 1.0
 * @since ������һ����������ӣ���������������ͬʱ��������ӵ�ʹ��
 */
public class CheckThread  implements Runnable {
	private static Log logger = LogFactory.getLog(CheckThread.class);	
	public void run() {
		while(true){
			MySocket socket = null;
			for(SocketEntity socketEntity : SocketKeep.socketEntityList){
				if(null != socketEntity && socketEntity.isKeepConn()){
					String isLock = SocketKeep.socketIsLock.get(socketEntity.getName());
					logger.warn("CheckThread " + isLock);
					// �����ǰδ��ʹ��
					if(!"1".equals(isLock)){
						// ��������
						SocketKeep.socketIsLock.put(socketEntity.getName(), "1");
						socket = SocketKeep.socketMap.get(socketEntity.getName());
						try {
							// ����һ��������
							socket.sendUrgentData(0xFF);
							// �ͷ���Դ
							SocketKeep.socketIsLock.put(socketEntity.getName(), "0");
						} catch (Exception e) {
							logger.error("�������ʱ�쳣��������������Դ���ƣ�" + socketEntity.getName(), e);
							// ����쳣��Ӧ�ý���һ���߳�ȥ��ʼ��������
							InitSocket initS = new InitSocket(socketEntity.getName());
							new Thread(initS).start();
						}
					}
				}
			}
			// ִ�м��
			try {
				logger.error("���μ�������");
				Thread.sleep(SocketKeep.commonCheckTime * 1000);
			} catch (Exception e) {
			}			
		}
	}
}