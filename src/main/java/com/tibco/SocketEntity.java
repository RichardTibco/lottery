package com.tibco;
/**
 * @˵�� ���ӵĹ�������
 * @author yangping
 * @version 1.0
 * @since
 */
public class SocketEntity {
	/**
	 * ���ӵ����֣���������ΪKey
	 */
	private String name;
	/**
	 * ���ӵ�IP
	 */
	private String ip;
	/**
	 * ���ӵĶ˿�
	 */
	private int port;
	/**
	 * �Ƿ񱣳�����
	 */
	private boolean keepConn;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isKeepConn() {
		return keepConn;
	}
	public void setKeepConn(boolean keepConn) {
		this.keepConn = keepConn;
	}
}