package yp.tibco.com.yang.lottery.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;
import java.util.UUID;

import yp.tibco.com.yang.lottery.json.bean.GetParameterBean;
    
public class RPCClient {
    
  private Connection connection;
  private Channel channel;
  private String requestQueueName = "rpc_queue";
  private String replyQueueName;
  private QueueingConsumer consumer;
    
  public RPCClient() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    connection = factory.newConnection();
    channel = connection.createChannel();

    replyQueueName = channel.queueDeclare().getQueue(); 
    consumer = new QueueingConsumer(channel);
    channel.basicConsume(replyQueueName, true, consumer);
  }
  
  public String call(String message) throws Exception {     
    String response = null;
    String corrId = UUID.randomUUID().toString();
    
    BasicProperties props = new BasicProperties
                                .Builder()
                                .correlationId(corrId)
                                .replyTo(replyQueueName)
                                .build();
    
    channel.basicPublish("", requestQueueName, props, message.getBytes());
    
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      if (delivery.getProperties().getCorrelationId().equals(corrId)) {
        response = new String(delivery.getBody(),"UTF-8");
        break;
      }
    }

    return response; 
  }
    
  public void close() throws Exception {
    connection.close();
  }
  
	public static void main(String[] argv) {
		RPCClient fibonacciRpc = null;
		String response = null;
		try {
			fibonacciRpc = new RPCClient();

			// System.out.println(" [x] Requesting fib(30)");
			System.out.println(" [x] Requesting lottery ");
			// response = fibonacciRpc.call("30");
			GetParameterBean bean = new GetParameterBean();
			bean.setGameId("123");
			bean.setTermID("ÄãºÃ");

			// String jsonA = "{\"TermID\":234, \"GameId\":456ÄãºÃ}";
			String jsonA = JSON.toJSONString(bean);
			response = fibonacciRpc.call(jsonA);
			System.out.println(" [.] CLIENT Got '" + response + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fibonacciRpc != null) {
				try {
					fibonacciRpc.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}