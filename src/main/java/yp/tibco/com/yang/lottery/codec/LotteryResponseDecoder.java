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

import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.imagine.step1.ImageResponse;

import yp.tibco.com.yang.lottery.message.HeaderMessage;
import yp.tibco.com.yang.lottery.message.LotteryRequest;
import yp.tibco.com.yang.lottery.message.LotteryResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * a decoder for {@link ImageResponse} objects 
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */

public class LotteryResponseDecoder extends AbstractMessageDecoder {



    @Override
    protected HeaderMessage decodeBody(IoSession session, IoBuffer in) {
        if (in.remaining() < MessageLength) {
            return null;
        }

        LotteryResponse m = new LotteryResponse();
        m.setXmlStr(ioBufferToString(in));
        return m;
    }

    public void finishDecode(IoSession session, ProtocolDecoderOutput out)
            throws Exception {
    }
    
    
    public static String ioBufferToString(IoBuffer ioBuffer)   
    {   
    	byte[] b = new byte[ioBuffer.limit()-ioBuffer.position()];   
    	Charset charset = Charset.forName("UTF-8");
    	CharsetDecoder cd = charset.newDecoder();
    	String str = null;
    	try {
			str = ioBuffer.getString(cd);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
    	return str;
    }
    
}
