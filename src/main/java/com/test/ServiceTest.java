package com.test;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @˵�� ����ˣ�ʼ�ս�������
 * @author yangping
 * @version 1.0
 * @since 
 */
public class ServiceTest {
	public static void main(String[] args) {
		try {
			ServerSocket ss1 = new ServerSocket(8001);
			Runnable accumelatora1 = new Accumulatort(ss1);
			Thread threada = new Thread(accumelatora1, "ThreadA");
			threada.start();
			System.out.println("����������ϣ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class Accumulatort implements Runnable {
	ServerSocket ss = null;
	public Accumulatort(ServerSocket s) {
		this.ss = s;
	}
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (true) {
				Socket s = ss.accept();
				System.out.println(format.format(new Date()) + " " + "---------�յ�����");
				new Thread(new ServiceImpl(s)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}