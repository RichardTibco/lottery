package yp.tibco.com.yang.lottery.mq;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.apache.commons.betwixt.io.BeanWriter;

import yp.tibco.com.App;
import yp.tibco.com.yang.lottery.client.LotteryClient;
import yp.tibco.com.yang.lottery.client.LotteryListener;
import yp.tibco.com.yang.lottery.codec.Constants;
import yp.tibco.com.yang.lottery.json.bean.GetParameterBean;
import yp.tibco.com.yang.lottery.json.bean.PurchaseBean;
import yp.tibco.com.yang.lottery.json.bean.QueryBean;
import yp.tibco.com.yang.lottery.message.LotteryRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;
  
public class RPCServer implements LotteryListener {
  
  private static final String RPC_QUEUE_NAME = "rpc_queue";
  
  private String respString = "";
  
  private boolean ready = false;
  
  public static int PORT = 33789;
  public static String HOST = "localhost";
  private LotteryClient lotteryClient = new LotteryClient(HOST, PORT, this);
  
  private static int fib(int n) {
    if (n ==0) return 0;
    if (n == 1) return 1;
    return fib(n-1) + fib(n-2);
  }
    
  

  public static void main(String[] argv) {
	  RPCServer server = new RPCServer();
	  
	  Properties properties = new Properties();
	  try {
		properties.load(App.class.getClassLoader().getResourceAsStream("settings.properties"));
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  
	  String str_host = (String) properties.get("host");
	  String str_port = (String) properties.get("port");
	  if(str_host!=null && !str_host.equals("") && isValidAddress(str_host)) {
		  
		  HOST = str_host;
	  }
	  if(str_port!=null && !str_port.equals("")) {
		  try {
			  int i_port = Integer.parseInt(str_port);
			  if(i_port < 65536) {
				  PORT = i_port;
			  }
		  } catch(NumberFormatException e) {
//			  e.printStackTrace();
		  }
	  }
	  
	  
	  server.connectServer();
      
	  server.process();
  }
  
  private static boolean isValidAddress(String serverAddress){
		if(serverAddress.equalsIgnoreCase("localhost"))
			return true;
		else if(serverAddress.contains(".")){ //$NON-NLS-1$
			StringTokenizer tokenizer = null;
			String token = null;
			tokenizer = new StringTokenizer(serverAddress,".");
			int i = 0;
			while(tokenizer.hasMoreTokens()){
				if(i > 3)
					return false;
				token = tokenizer.nextToken();
				if(!isValidInteger(token))
					return false;
				if(token.length()>3) {
					return false;
				}
				i++;
			}
			return true;
		}
		return true;
	}
  
  private static boolean isValidInteger(String no){
		try {
			Integer.parseInt(no);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

private void connectServer() {
	if (lotteryClient != null) {
        lotteryClient.disconnect();
    }
    lotteryClient = new LotteryClient(HOST, PORT, this);
    lotteryClient.connect();
}

private void process() {
	Connection connection = null;
    Channel channel = null;
    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
  
      connection = factory.newConnection();
      channel = connection.createChannel();
      
      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
  
      channel.basicQos(1);
  
      QueueingConsumer consumer = new QueueingConsumer(channel);
      channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
  
      System.out.println(" [x] Awaiting RPC requests");
  
      while (true) {
        String response = null;
        
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        
        BasicProperties props = delivery.getProperties();
        BasicProperties replyProps = new BasicProperties
                                         .Builder()
                                         .correlationId(props.getCorrelationId())
                                         .build();
        
        try {
          String message = new String(delivery.getBody(),"UTF-8");
//          int n = Integer.parseInt(message);
  
          System.out.println(" [.] SERVER (" + message + ")");
          
          //json --->bean
          GetParameterBean getBean = JSON.parseObject(message, GetParameterBean.class);
          
          
          //bean  ---> xml
          String xmlStr = Object2XmlString(getBean);
          LotteryRequest msg = new LotteryRequest();
          msg.setTransType((byte)1);
          msg.setFromID(Constants.FROM_ID_MOBILE_SMS);
          msg.setMessageLength((short)(xmlStr.getBytes().length));
          msg.setStatus(1);
          msg.setSequenceNumber(12);
          msg.setReserve(1);
          msg.setXmlStr(xmlStr);
//          response = "" + fib(n);
          lotteryClient.sendRequest(msg);
          
          while (!ready) {
        	  Thread.sleep(100);
        	  System.out.println("sleep...");
          }
          
          response = respString;
        }
        catch (Exception e){
          System.out.println(" [.] " + e.toString());
          response = "";
        }
        finally {  
          channel.basicPublish( "", props.getReplyTo(), replyProps, response.getBytes("UTF-8"));
  
          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
          
          ready = false;
        }
      }
    }
    catch  (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (connection != null) {
        try {
          connection.close();
        }
        catch (Exception ignore) {}
      }
    }
}

private static String Object2XmlString(Object object) {
	String xmlString = null;

    StringWriter outputWriter = new StringWriter();
    outputWriter.write("<?xml version='1.0' encoding='gb2312' ?>");
    BeanWriter beanWriter = new BeanWriter(outputWriter);
    beanWriter.getXMLIntrospector().setAttributesForPrimitives(false);
    beanWriter.setWriteIDs(false);


    try {

     beanWriter.write("webinf", object);

    } catch (Exception e) {

     e.printStackTrace();

    }

    xmlString = outputWriter.toString();
	return xmlString;
	
}



@Override
public void onImages(BufferedImage image1, BufferedImage image2) {
	
}



@Override
public void onException(Throwable throwable) {
	
	Throwable cause = throwable;
    while (cause.getCause() != null) {
        cause = cause.getCause();
    }
    System.out.println(cause);
    System.exit(0);
//    JOptionPane.showMessageDialog(
//            this,
//            cause.getMessage(),
//            throwable.getMessage(),
//            JOptionPane.ERROR_MESSAGE);
	
}



@Override
public void sessionOpened() {
	// TODO Auto-generated method stub
	
}



@Override
public void sessionClosed() {
	// TODO Auto-generated method stub
	
}



@Override
public void onMessageArrival(String str) {

	respString = str;
	ready = true;
}
}