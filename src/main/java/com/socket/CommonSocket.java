package com.socket;
import java.io.IOException;
import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @说明 对外提供的方法
 * @author yangping
 * @version 1.0
 * @since 得到某个资源使用后必须释放，否则造成霸占使用
 */
public class CommonSocket {
	private static Log logger = LogFactory.getLog(CommonSocket.class);
	/**
	 * @说明 通过名字来获得某个连接对象
	 * @since 如果返回为空，你应该尝试反复调用，因为该对象可能被检测机制占用，或当前不可用
	 * @since 如果你通过该方法获得了连接对象，那么你应该在使用后显示的关闭该对象的使用，否则该对象会死锁
	 * @since 如果你尝试获得一个没有被定义的连接，那么会返回NULL
	 */
	public static Socket getSocketByName(String name) {
		MySocket socket = null;
		boolean isHave = false;// 是否有对象的定义
		boolean isKeep = false;// 该对象是否为单例
		// 检测是否有某个连接的配置信息
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
		// 是否有该名字的配置
		if (isHave) {
			if (isKeep) {
				String isLock = SocketKeep.socketIsLock.get(name);
				logger.warn("getSocketByName " + name + "  "+ isLock);
				logger.warn("getSocketByName " + "socket value: " + "  "+ SocketKeep.socketMap.get(name).toString());
				// 如果当前未锁定
				if (null != isLock && !"1".equals(isLock)) {
					// 设置占用
					SocketKeep.socketIsLock.put(name, "1");
					socket = SocketKeep.socketMap.get(name);
					try {
						// 发送一个心跳包
						socket.sendUrgentData(0xFF);
					} catch (Exception e) {
						logger.error("得到资源后发现资源不可用！资源名称：" + name);
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
					logger.warn("当前连接正被占用，请稍候尝试！资源名称：" + name);
				}
			} else {
				logger.warn("为用户建立请求连接！资源名称：" + name);
				// 建立一次性连接
				return initSocketByName(name);
			}
		} else {
			logger.error("没有关于该名称的配置信息！资源名称：" + name);
		}
		return socket;
	}
	/**
	 * @说明 根据名称建立一个连接
	 * @since 需要被创建的连接必须已经加载了配置的信息
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
						// 发送一个心跳包
						socket.sendUrgentData(0xFF);
					} catch (Exception e) {
						logger.error("方法initSocketByName建立连接时错误！", e);
						socket = null;
					}
				}
			}
		}
		return socket;
	}
}