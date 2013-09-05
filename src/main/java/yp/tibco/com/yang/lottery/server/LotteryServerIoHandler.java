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
package yp.tibco.com.yang.lottery.server;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.imagine.step1.ImageRequest;
import org.apache.mina.example.imagine.step1.ImageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import yp.tibco.com.yang.lottery.json.bean.GetParameterBean;
import yp.tibco.com.yang.lottery.json.bean.GetParameterBeanBody;
import yp.tibco.com.yang.lottery.message.LotteryRequest;
import yp.tibco.com.yang.lottery.message.LotteryResponse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringReader;

/**
 * server-side {@link org.apache.mina.core.service.IoHandler}
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */

public class LotteryServerIoHandler extends IoHandlerAdapter {

    private final static String characters = "mina rocks abcdefghijklmnopqrstuvwxyz0123456789";

    public static final String INDEX_KEY = LotteryServerIoHandler.class.getName() + ".INDEX";

    private static Logger LOGGER = LoggerFactory.getLogger(LotteryServerIoHandler.class);

    /**
     * Called when the session is opened, which will come after the session created.
     * 
     * @see org.apache.mina.core.service.IoHandlerAdapter#sessionOpened(org.apache.mina.core.session.IoSession)
     */
    public void sessionOpened(IoSession session) throws Exception {
        session.setAttribute(INDEX_KEY, 0);
    }

    /**
     * This method will be called whenever an exception occurs.  For this handler,
     * the logger will generate a warning message.
     * 
     * @see org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache.mina.core.session.IoSession, java.lang.Throwable)
     */
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        LOGGER.warn(cause.getMessage(), cause);
    }

    /**
     * Handle incoming messages.
     * 
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    public void messageReceived(IoSession session, Object message) throws Exception {
//        ImageRequest request = (ImageRequest) message;
//        String text1 = generateString(session, request.getNumberOfCharacters());
//        String text2 = generateString(session, request.getNumberOfCharacters());
//        BufferedImage image1 = createImage(request, text1);
//        BufferedImage image2 = createImage(request, text2);
//        ImageResponse response = new ImageResponse(image1, image2);
//        session.write(response);
    	LotteryRequest request = (LotteryRequest) message;
    	if(request.getTransType() == 0) {
    		return;
    	}
    	String rt = "\n";
    	String nb = "    ";
    	System.out.print("request is =" + rt + nb
    						+"TransType : " + request.getTransType() + ", " + rt + nb
    						+"FromID : " + request.getFromID() + ", " + rt + nb
    						+"MessageLength : " + request.getMessageLength() + ", " + rt + nb
    						+"Status : " + request.getStatus() + ", " + rt + nb
    						+"SequenceNumber : " +request.getSequenceNumber() + ", " + rt + nb
    						+"Message content : " + rt 
    						+ request.getXmlStr() + rt
    						);
    	String respContent = "<?xml version=\'1.0\' encoding=\'gb2312\' ?><webinf><GameName>B001</GameName><SaleName>shuangseqiu</SaleName><GameType>2</GameType><GameId>2</GameId><BetPerTick>5</BetPerTick><SpecialNum>1</SpecialNum><NumPerBet>7</NumPerBet><MultiDraw>0</MultiDraw><MultiFlag>50</MultiFlag><SalePerm>3</SalePerm><MoneyPerBet>200</MoneyPerBet><ShowDraw>2014029</ShowDraw><StartTime>20140317073000</StartTime><EndTime>20140318200000</EndTime><MinDan>1</MinDan><MaxDan>5</MaxDan><MinTuo>2</MinTuo><MaxTuo>32</MaxTuo><CmaxNum>33</CmaxNum><CminNum>1</CminNum><CSpMaxNum>16</CSpMaxNum><CSpMinNum>1</CSpMinNum></webinf>";
    	System.out.println("----------------->" + request.getXmlStr());
    	GetParameterBeanBody requestBean = (GetParameterBeanBody) xmlString2Object(request.getXmlStr(), "webinf" , GetParameterBeanBody.class);
    	LotteryResponse response = new LotteryResponse();
    	response.setTransType(request.getTransType());
    	response.setFromID(request.getFromID());
    	response.setMessageLength((short)(respContent.getBytes().length));
    	response.setStatus(22);
    	response.setSequenceNumber(request.getSequenceNumber());
    	response.setXmlStr(respContent);
    	Thread.sleep(1000);
    	session.write(response);
    }

    
    
    public Object xmlString2Object(String xmlString ,String className,Class cl) {


        StringReader xmlReader = new StringReader(xmlString);

        BeanReader beanReader = new BeanReader();


        beanReader.getXMLIntrospector().setAttributesForPrimitives(false); 

	   try {
	
	               //beanReader.registerBeanClass("SelectUserIDListBean", SelectUserIDListBean.class);
	
	               beanReader.registerBeanClass(className,cl);
	
	     } catch (IntrospectionException e1) {
	
	               // TODO Auto-generated catch block
	
	               e1.printStackTrace();
	
	     } 

        // ��XML����Java Object

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
    
    /**
     * Create an image using the specified request and the text.  
     *
     * @param request
     *  Determines the height and width of the image
     * @param text
     *  The text that is placed in the image
     * @return
     *  a BufferedImage representing the text.
     */
    private BufferedImage createImage(ImageRequest request, String text) {
        BufferedImage image = new BufferedImage(request.getWidth(), request.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        Graphics graphics = image.createGraphics();
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        Font serif = new Font("serif", Font.PLAIN, 30);
        graphics.setFont(serif);
        graphics.setColor(Color.BLUE);
        graphics.drawString(text, 10, 50);
        return image;
    }

    /**
     * Generate a string based on the 'characters' field in this class.  The
     * characters that make up the string are based on the session 
     * attribute "INDEX_KEY"
     *
     * @param session
     *  The {@link IoSession} object that will provide the INDEX_KEY attribute
     * @param length
     *  The length that the String will be
     * @return
     *  The generated String
     */
    private String generateString(IoSession session, int length) {
        Integer index = (Integer) session.getAttribute(INDEX_KEY);
        StringBuffer buffer = new StringBuffer(length);
        while (buffer.length() < length) {
            buffer.append(characters.charAt(index));
            index++;
            if (index >= characters.length()) {
                index = 0;
            }
        }
        session.setAttribute(INDEX_KEY, index);
        return buffer.toString();
    }

}
