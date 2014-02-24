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
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.sumup.message.AddMessage;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import yp.tibco.com.yang.lottery.message.LotteryRequest;

/**
 * an encoder for {@link ImageRequest} objects 
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * @param <T>
 */

public class LotteryRequestEncoder<T extends LotteryRequest> extends AbstractMessageEncoder<T> {

	public LotteryRequestEncoder(){
		
	}
//    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
//    	LotteryRequest request = (LotteryRequest) message;
//        IoBuffer buffer = IoBuffer.allocate(16+request.getMessageLength(), false);
//        buffer.put(request.getTransType());
//        buffer.put(request.getFromID());
//        buffer.putShort(request.getMessageLength());
//        buffer.putInt(request.getStatus());
//        buffer.putInt(request.getSequenceNumber());
//        buffer.putInt(request.getReserve());
//        byte bt[] = request.getXmlStr().getBytes();
//        buffer.put(bt,0,bt.length);
//        buffer.flip();
//        out.write(buffer);
//    }
    
    @Override
    protected void encodeBody(IoSession session, T message, IoBuffer out) {
//    	byte bt[] = message.getXmlStr().getBytes();
//        out.put(bt,0,bt.length);
    	Charset charset = Charset.forName("UTF-8");
    	CharsetEncoder ce = charset.newEncoder();
    	try {
			out.putString(message.getXmlStr(), ce);
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public void dispose(IoSession session) throws Exception {
        // nothing to dispose
    }
}
