package com.test;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.socket.CommonSocket;
import com.socket.MySocket;
import com.socket.SocketKeep;
/**
 * @˵�� ѭ��ȥ��������Ӧ������Ȼ���ӡ���ӵ�ַ
 * @author yangping
 * @version 1.0
 * @since 
 */
public class GetSocketTest {
	public static void main(String[] args) {
		SocketKeep.initSocketKeep();
		while(true){			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {				
				Socket socket1 = CommonSocket.getSocketByName("socket1");
				if(null != socket1){
					System.out.println(format.format(new Date()) + " "+((MySocket)socket1).getName() + " " + socket1.toString());
					socket1.close();	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//			try {
//				Socket socket2 = CommonSocket.getSocketByName("socket2");
//				if(null != socket2){
//					System.out.println(format.format(new Date()) + " "+((SocketCui)socket2).getName() + " "  + socket2.toString());
//					socket2.close();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				
			}
		}
	}
}