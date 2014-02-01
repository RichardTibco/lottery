package com.tibco;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @˵�� ��������
 * @author yangping
 * @version 1.0
 * @since
 */
public class SocketKeep {
	private static Log logger = LogFactory.getLog(SocketKeep.class);
	/**
	 * ������Ϣ����
	 */
	public static List<SocketEntity> socketEntityList = new ArrayList<SocketEntity>();
	/**
	 * ���Ӷ��󱣳֣�ֻ������Ҫϵͳ���ֵ�����
	 */
	/**
	 * �������Ӽ����
	 */
	public static int commonCheckTime = 2;
	/**
	 * ���ӵ�������һ��Ҫ��ʵ�����õ�����ƥ��
	 */
	public static int socketConnCount = 0;	
	public static ExecutorService executorService = null;// �̳߳�
	/**
	 * ��ʼ������������Ϣ
	 */
	public static void initSocketKeep() {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(SocketKeep.class.getClassLoader().getResourceAsStream("socket.properties"));
			logger.warn("����socket.properties�ļ��ɹ���");
		} catch (Exception e) {
			logger.error("����socket.properties�ļ�ʧ�ܣ�", e);
			properties = null;
		}
		if (null != properties) {
			try {
				commonCheckTime = Integer.parseInt(properties.getProperty("commonCheckTime"));
				socketConnCount = Integer.parseInt(properties.getProperty("socketConnCount"));
				executorService = Executors.newFixedThreadPool(socketConnCount + 1);
			} catch (Exception e) {
				executorService = Executors.newFixedThreadPool(1);
				logger.error("����������Ϣʱ����", e);
				// ϵͳ�������������Եļ����쳣
			}
			SocketEntity socketEntity = null;
			for (int i = 1; i <= socketConnCount; i++) {
				String name = properties.getProperty("socket" + i);
				if(null != name){
					socketEntity = new SocketEntity();
					String ip = properties.getProperty("socket" + i + "_ip");
					String port = properties.getProperty("socket" + i + "_port");
					String isKeep = properties.getProperty("socket" + i + "_isKeep");
					
					socketEntity.setName(name);
					socketEntity.setIp(ip);
					socketEntity.setPort(Integer.parseInt(port));
					boolean keepConn = false;
					if(null != isKeep && "1".equals(isKeep)){
						keepConn = true;
					}
					socketEntity.setKeepConn(keepConn);	
					socketEntityList.add(socketEntity);
				}
			}
		}
		logger.warn("����Socket����������Ϣ������");		
		logger.warn("��ʼ��ʼ��Socket���ӣ�");
		
		
//		MySocket socket = null;
//		for(SocketEntity socketEntity : socketEntityList){
//			if(null != socketEntity && socketEntity.isKeepConn()){
//				try {
//					socket = new MySocket(socketEntity.getIp(),socketEntity.getPort());
//					socket.setSoTimeout(0);
//					socket.setKeepAlive(true);
//					socket.setName(socketEntity.getName());
//				} catch (Exception e) {
//					logger.error("��ʼ��ĳ������ʱ���󣡴�������ӽ���������Դ���ƣ�" + socketEntity.getName(), e);
//					socket = null;
//				}
//				if(null != socket){
//					socketMap.put(socketEntity.getName(), socket);
//				}else{
//					socketMap.put(socketEntity.getName(), new MySocket());					
//				}
//				socketIsLock.put(socketEntity.getName(), "0");
//			}
//		}		
//		// ��ʼִ�м��
//		executorService.execute(new CheckThread());
		logger.warn("��ʼ��Socket���ӽ�����");
	}
}