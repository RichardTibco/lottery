/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package yp.tibco.com.yang.lottery.client;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.imagine.step1.ImageRequest;
import org.apache.mina.example.imagine.step1.ImageResponse;
import org.apache.mina.example.imagine.step1.server.ImageServer;
import org.apache.mina.example.imagine.step1.codec.ImageCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import yp.tibco.com.yang.lottery.codec.Constants;
import yp.tibco.com.yang.lottery.codec.LotteryCodecFactory;
import yp.tibco.com.yang.lottery.message.LotteryRequest;
import yp.tibco.com.yang.lottery.message.LotteryResponse;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * client for the {@link ImageServer}
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class LotteryClient extends IoHandlerAdapter {
    public static final int CONNECT_TIMEOUT = 3000;

    private String host;
    private int port;
    private SocketConnector connector;
    private IoSession session;
    private LotteryListener imageListener;

    public LotteryClient(String host, int port, LotteryListener imageListener) {
        this.host = host;
        this.port = port;
        this.imageListener = imageListener;
        connector = new NioSocketConnector();
//        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ImageCodecFactory(true)));
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new LotteryCodecFactory(true)));
        connector.setHandler(this);
        connector.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, 5);
    }


	public boolean isConnected() {
        return (session != null && session.isConnected());
    }

    public void connect() {
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(host, port));
        connectFuture.awaitUninterruptibly(CONNECT_TIMEOUT);
        try {
            session = connectFuture.getSession();
        }
        catch (RuntimeIoException e) {
            imageListener.onException(e);
        }
    }

    public void disconnect() {
        if (session != null) {
            session.close(true).awaitUninterruptibly(CONNECT_TIMEOUT);
            session = null;
        }
    }

    public void sessionOpened(IoSession session) throws Exception {
        imageListener.sessionOpened();
    }

    public void sessionClosed(IoSession session) throws Exception {
        imageListener.sessionClosed();
    }
    
    @Override
    public void sessionIdle(IoSession iosession, IdleStatus idlestatus)
    		throws Exception {
    	
    	
    	if (session == null) {
            //noinspection ThrowableInstanceNeverThrown
            imageListener.onException(new Throwable("not connected"));
        } else {
        	String xmlStr = "<?xml version=\"1.0\" encoding=\"gb2312\" ?><webinf></webinf>";
        	LotteryRequest lotteryRequest = new LotteryRequest();
        	lotteryRequest.setTransType((byte)0);
        	lotteryRequest.setFromID(Constants.FROM_ID_MOBILE_SMS);
        	lotteryRequest.setMessageLength((short)(xmlStr.getBytes().length));
        	lotteryRequest.setStatus(1);
        	lotteryRequest.setSequenceNumber(12);
        	lotteryRequest.setReserve(1);
        	lotteryRequest.setXmlStr(xmlStr);
            
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	System.out.println("IDLE " + df.format(new Date()));
        	
        	
            session.write(lotteryRequest);
        }
    	
    	
    	
    	
        
    }

    public void sendRequest(LotteryRequest lotteryRequest) {
        if (session == null) {
            //noinspection ThrowableInstanceNeverThrown
            imageListener.onException(new Throwable("not connected"));
        } else {
            session.write(lotteryRequest);
        }
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
//        ImageResponse response = (ImageResponse) message;
//        imageListener.onImages(response.getImage1(), response.getImage2());
    	LotteryResponse response = (LotteryResponse) message;
//    	String respStr = "response is =[ "
//		+" TransType : " + response.getTransType()
//		+", FromID : " + response.getFromID() 
//		+", MessageLength : " + response.getMessageLength()
//		+", Status : " + response.getStatus()
//		+", SequenceNumber : " +response.getSequenceNumber()
//		+"Message content : " +  response.getXmlStr()
//		+"]";
    	
    	String rt = "\n";
    	String nb = "    ";
    	String respStr =  ("response is =" + rt + nb
    						+"TransType : " + response.getTransType() + ", " + rt + nb
    						+"FromID : " + response.getFromID() + ", " + rt + nb
    						+"MessageLength : " + response.getMessageLength() + ", " + rt + nb
    						+"Status : " + response.getStatus() + ", " + rt + nb
    						+"SequenceNumber : " +response.getSequenceNumber() + ", " + rt + nb
    						+"Message content : " + rt + nb
    						+ "  " +   response.getXmlStr()
    						);
    	System.out.println(respStr);
		
    	imageListener.onMessageArrival(respStr);
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        imageListener.onException(cause);
    }

}
