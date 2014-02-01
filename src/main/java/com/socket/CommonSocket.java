package com.socket;
import java.io.IOException;
import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @˵�� �����ṩ�ķ���
 * @author yangping
 * @version 1.0
 * @since �õ�ĳ����Դʹ�ú�����ͷţ�������ɰ�ռʹ��
 */
public class CommonSocket {
	private static Log logger = LogFactory.getLog(CommonSocket.class);
	/**
	 * @˵�� ͨ�����������ĳ�����Ӷ���
	 * @since �������Ϊ�գ���Ӧ�ó��Է������ã���Ϊ�ö�����ܱ�������ռ�ã���ǰ������
	 * @since �����ͨ���÷�����������Ӷ�����ô��Ӧ����ʹ�ú���ʾ�Ĺرոö����ʹ�ã�����ö��������
	 * @since ����㳢�Ի��һ��û�б���������ӣ���ô�᷵��NULL
	 */
	public static Socket getSocketByName(String name) {
		MySocket socket = null;
		boolean isHave = false;// �Ƿ��ж���Ķ���
		boolean isKeep = false;// �ö����Ƿ�Ϊ����
		// ����Ƿ���ĳ�����ӵ�������Ϣ
		for (SocketEntity socketEntity : SocketKeep.socketEntityList) {
			if (null != socketEntity) {
				if (socketEntity.getName().equals(name)) {
					isHave = true;
					if (socketEntity.isKeepConn()) {
						isKeep = true;
					}
					break;
				}
			}
		}
		// �Ƿ��и����ֵ�����
		if (isHave) {
			if (isKeep) {
				String isLock = SocketKeep.socketIsLock.get(name);
				logger.warn("getSocketByName " + name + "  "+ isLock);
				logger.warn("getSocketByName " + "socket value: " + "  "+ SocketKeep.socketMap.get(name).toString());
				// �����ǰδ����
				if (null != isLock && !"1".equals(isLock)) {
					// ����ռ��
					SocketKeep.socketIsLock.put(name, "1");
					socket = SocketKeep.socketMap.get(name);
					try {
						// ����һ��������
						socket.sendUrgentData(0xFF);
					} catch (Exception e) {
						logger.error("�õ���Դ������Դ�����ã���Դ���ƣ�" + name);
//						e.printStackTrace();
//						socket = null;
						try {
							socket.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					logger.warn("��ǰ��������ռ�ã����Ժ��ԣ���Դ���ƣ�" + name);
				}
			} else {
				logger.warn("Ϊ�û������������ӣ���Դ���ƣ�" + name);
				// ����һ��������
				return initSocketByName(name);
			}
		} else {
			logger.error("û�й��ڸ����Ƶ�������Ϣ����Դ���ƣ�" + name);
		}
		return socket;
	}
	/**
	 * @˵�� �������ƽ���һ������
	 * @since ��Ҫ�����������ӱ����Ѿ����������õ���Ϣ
	 */
	private static Socket initSocketByName(String name) {
		Socket socket = null;
		for (SocketEntity socketEntity : SocketKeep.socketEntityList) {
			if (null != socketEntity) {
				if (socketEntity.getName().equals(name)) {
					try {
						socket = new MySocket(socketEntity.getIp(), socketEntity.getPort());
						socket.setSoTimeout(0);
						socket.setKeepAlive(true);
						// ����һ��������
						socket.sendUrgentData(0xFF);
					} catch (Exception e) {
						logger.error("����initSocketByName��������ʱ����", e);
						socket = null;
					}
				}
			}
		}
		return socket;
	}
}