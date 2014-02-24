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
package yp.tibco.com.yang.lottery.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.imagine.step1.ImageRequest;
import org.apache.mina.example.sumup.codec.Constants;
import org.apache.mina.example.sumup.message.AbstractMessage;
import org.apache.mina.example.sumup.message.AddMessage;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import yp.tibco.com.yang.lottery.message.HeaderMessage;
import yp.tibco.com.yang.lottery.message.LotteryRequest;

/**
 * a decoder for {@link ImageRequest} objects
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class LotteryRequestDecoder extends AbstractMessageDecoder {
	


	@Override
    protected HeaderMessage decodeBody(IoSession session, IoBuffer in) {
        if (in.remaining() < MessageLength) {
            return null;
        }

        LotteryRequest m = new LotteryRequest();
//        m.setXmlStr(in.get(MessageLength))
        m.setXmlStr(ioBufferToString(in));
        return m;
    }

    public void finishDecode(IoSession session, ProtocolDecoderOutput out)
            throws Exception {
    }
    
//    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
//        if (in.remaining() >= Constants.HEADER_LEN) {
//        	HeaderMessage head = new HeaderMessage();
//        	head.setTransType(in.get());
//        	head.setFromID(in.get());
//        	head.setMessageLength(in.getShort());
//        	head.setStatus(in.getInt());
//        	head.setSequenceNumber(in.getInt());
//        	head.setReserve(in.getInt());
//        	
//        	if(in.remaining() >= head.getMessageLength()) {
//        		LotteryRequest request = new LotteryRequest(head);
//        		request.setXmlStr(ioBufferToString(in));
//        		out.write(request);
//        		return true;
//        	} else {
//        		return false;
//        	}
////            out.write(request);
////            return true;
//        } else {
//            return false;
//        }
//    }
//    public static String ioBufferToString(IoBuffer ioBuffer)   
//    {   
//          byte[] b = new byte [ioBuffer.limit()];   
//          ioBuffer.get(b);   
//          StringBuffer stringBuffer = new StringBuffer();   
//      
//          for (int i = 0; i < b.length; i++)   
//          {   
//      
//           stringBuffer.append((char) b [i]);   
//          }   
//           return stringBuffer.toString();   
//    }
    
    public static String ioBufferToString(IoBuffer ioBuffer)   
    {   
    	byte[] b = new byte[ioBuffer.limit()-ioBuffer.position()];   
//    	ioBuffer.get(b); 
    	Charset charset = Charset.forName("UTF-8");
    	CharsetDecoder cd = charset.newDecoder();
    	String str = null;
    	try {
			str = ioBuffer.getString(cd);
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return str;
//          StringBuffer stringBuffer = new StringBuffer();   
//      
//          for (int i = 0; i < b.length; i++)   
//          {   
//      
//           stringBuffer.append((char) b [i]);   
//          }   
//           return stringBuffer.toString();   
    }

}
