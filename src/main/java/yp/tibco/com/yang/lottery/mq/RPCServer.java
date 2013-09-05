package yp.tibco.com.yang.lottery.mq;

import java.awt.image.BufferedImage;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.CapitalizeNameMapper;
import org.apache.commons.betwixt.strategy.HyphenatedNameMapper;
import org.xml.sax.SAXException;

import yp.tibco.com.App;
import yp.tibco.com.yang.lottery.client.LotteryClient;
import yp.tibco.com.yang.lottery.client.LotteryListener;
import yp.tibco.com.yang.lottery.codec.Constants;
import yp.tibco.com.yang.lottery.json.bean.GetParameterBean;
import yp.tibco.com.yang.lottery.json.bean.GetParameterRespBean;
import yp.tibco.com.yang.lottery.json.bean.GetParameterRespBeanBody;
import yp.tibco.com.yang.lottery.json.bean.PurchaseBean;
import yp.tibco.com.yang.lottery.json.bean.PurchaseBeanBody;
import yp.tibco.com.yang.lottery.json.bean.PurchaseRespBean;
import yp.tibco.com.yang.lottery.json.bean.PurchaseRespBeanBody;
import yp.tibco.com.yang.lottery.json.bean.QueryBean;
import yp.tibco.com.yang.lottery.json.bean.QueryRespBean;
import yp.tibco.com.yang.lottery.json.bean.QueryRespBeanBody;
import yp.tibco.com.yang.lottery.message.LotteryRequest;
import yp.tibco.com.yang.lottery.message.MessageHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
  
public class RPCServer implements LotteryListener {
  
  private static final String RPC_QUEUE_NAME = "rpc_queue";
  
  private String respString = "";
  
  private boolean ready = false;
  
  public static int PORT = 33789;
  public static String HOST = "localhost";
  
//  public static int PORT = 7100;
//  public static String HOST = "61.144.230.19";
  
  private LotteryClient lotteryClient = new LotteryClient(HOST, PORT, this);
  private Integer seq = 0;
  
  public Integer getSeq() {
	  if(seq < Integer.MAX_VALUE){
		  seq++;
			
		}else {
			seq = 0;
		}
	return seq;
}




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
			factory.setHost("192.168.82.186");

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
				BasicProperties replyProps = new BasicProperties.Builder()
						.correlationId(props.getCorrelationId()).build();

				try {
					String message = new String(delivery.getBody(), "GBK");
					// int n = Integer.parseInt(message);

					System.out.println(" [.] SERVER (" + message + ")");

					// json --->bean
					JSONObject obj = (JSONObject) JSON.parse(message);
//					String transType = (String) obj.get("transType");
//					Integer i_transType = Integer.parseInt(transType);
					Integer i_transType = obj.getInteger("transType");
					if (i_transType == Constants.TRANS_TYPE_GET_GAME_PARAM) {
						GetParameterBean getBean = JSON.parseObject(message, GetParameterBean.class);

						// bean ---> xml
						String xmlStr = Object2XmlString(getBean.getData());
						LotteryRequest msg = new LotteryRequest();
						msg.setTransType(getBean.getTransType());
						msg.setFromID(Constants.FROM_ID_MOBILE_SMS);
						msg.setMessageLength((short) (xmlStr.getBytes().length));
						msg.setStatus(1);
						msg.setSequenceNumber(getSeq());
						msg.setReserve(1);
						msg.setXmlStr(xmlStr);
						lotteryClient.sendRequest(msg);
					} else if (i_transType == Constants.TRANS_TYPE_PURCHASE) {
						PurchaseBean getBean = JSON.parseObject(message, PurchaseBean.class);

						// bean ---> xml
						String xmlStr = Object2XmlString(getBean.getData());
						LotteryRequest msg = new LotteryRequest();
						msg.setTransType(getBean.getTransType());
						msg.setFromID(Constants.FROM_ID_MOBILE_SMS);
						msg.setMessageLength((short) (xmlStr.getBytes().length));
						msg.setStatus(1);
						msg.setSequenceNumber(getSeq());
						msg.setReserve(1);
						msg.setXmlStr(xmlStr);
						lotteryClient.sendRequest(msg);
					} else if (i_transType == Constants.TRANS_TYPE_QUERY) {
						QueryBean getBean = JSON.parseObject(message, QueryBean.class);

						// bean ---> xml
						String xmlStr = Object2XmlString(getBean.getData());
						LotteryRequest msg = new LotteryRequest();
						msg.setTransType(getBean.getTransType());
						msg.setFromID(Constants.FROM_ID_MOBILE_SMS);
						msg.setMessageLength((short) (xmlStr.getBytes().length));
						msg.setStatus(1);
						msg.setSequenceNumber(getSeq());
						msg.setReserve(1);
						msg.setXmlStr(xmlStr);
						lotteryClient.sendRequest(msg);
					} else {
						
					}

					while (!ready) {
						Thread.sleep(100);
						System.out.println("sleep...");
					}

					response = respString;
				} catch (Exception e) {
					System.out.println(" [.] " + e.toString());
					response = "";
				} finally {
					channel.basicPublish("", props.getReplyTo(), replyProps,
							response.getBytes("GBK"));

					channel.basicAck(delivery.getEnvelope().getDeliveryTag(),
							false);

					ready = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

private static String Object2XmlString(Object object) {
	String xmlString = null;

    StringWriter outputWriter = new StringWriter();
    outputWriter.write("<?xml version='1.0' encoding='gb2312' ?>");
    BeanWriter beanWriter = new BeanWriter(outputWriter);
    beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
//    beanWriter.getBindingConfiguration().setMapIDs(false);


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
	//TODO transform message to json

	
//	GetParameterBean responseBean = (GetParameterBean) xmlString2Object(str, "webinf" , GetParameterBean.class);
//	respString = JSON.toJSONString(responseBean);

	MessageHelper messageHelper = new MessageHelper();
	messageHelper.setStringToMessage(str);
	
	int ttNo = messageHelper.getTransType();
	
	switch (ttNo) {
	case 1:
		GetParameterRespBeanBody responseBean1 = (GetParameterRespBeanBody) xmlString2Object(messageHelper.getContent().trim(), "webinf" , GetParameterRespBeanBody.class);
		GetParameterRespBean response1 = new GetParameterRespBean();
		response1.setData(responseBean1);
		response1.setFromID(messageHelper.getFromID());
		response1.setMessage_Length(messageHelper.getMessageLength().intValue());
		response1.setSequence_Number(messageHelper.getSequenceNumber());
		response1.setStatus(messageHelper.getStatus());
		response1.setTransType(messageHelper.getTransType());
		respString = JSON.toJSONString(response1);
		break;
	case 3:
		PurchaseRespBeanBody responseBean2 = (PurchaseRespBeanBody) xmlString2Object(messageHelper.getContent().trim(), "webinf" , PurchaseRespBeanBody.class);
		PurchaseRespBean response2 = new PurchaseRespBean();
		response2.setData(responseBean2);
		response2.setFromID(messageHelper.getFromID());
		response2.setMessage_Length(messageHelper.getMessageLength().intValue());
		response2.setSequence_Number(messageHelper.getSequenceNumber());
		response2.setStatus(messageHelper.getStatus());
		response2.setTransType(messageHelper.getTransType());
		respString = JSON.toJSONString(response2);
		break;
	case 8:
		QueryRespBeanBody responseBean3 = (QueryRespBeanBody) xmlString2Object(messageHelper.getContent().trim(), "webinf" , QueryRespBeanBody.class);
		QueryRespBean response3 = new QueryRespBean();
		response3.setData(responseBean3);
		response3.setFromID(messageHelper.getFromID());
		response3.setMessage_Length(messageHelper.getMessageLength().intValue());
		response3.setSequence_Number(messageHelper.getSequenceNumber());
		response3.setStatus(messageHelper.getStatus());
		response3.setTransType(messageHelper.getTransType());
		respString = JSON.toJSONString(response3);
		break;
	default:
		break;
	}
	
//	respString = str;
	ready = true;
}

public Object xmlString2Object(String xmlString ,String className,Class cl) {
    StringReader xmlReader = new StringReader(xmlString);
    BeanReader beanReader = new BeanReader();
    beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false); 
    CapitalizeNameMapper mapper = new CapitalizeNameMapper();
    beanReader.getXMLIntrospector().getConfiguration().setAttributeNameMapper(mapper);

   try {
               beanReader.registerBeanClass(className,cl);

     } catch (IntrospectionException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
     } 
    Object obj = null;
    try {
     obj = beanReader.parse(xmlReader);
    } catch (IOException e) {
     e.printStackTrace();

    } catch (SAXException e) {
     e.printStackTrace();
    }
    return obj;
 }

}