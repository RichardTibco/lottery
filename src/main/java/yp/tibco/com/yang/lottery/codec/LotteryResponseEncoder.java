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

import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.imagine.step1.ImageResponse;

import yp.tibco.com.yang.lottery.message.LotteryRequest;
import yp.tibco.com.yang.lottery.message.LotteryResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * an encoder for {@link ImageResponse} objects 
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class LotteryResponseEncoder<T extends LotteryResponse> extends AbstractMessageEncoder<T> {

	public LotteryResponseEncoder() {
		
	}
//    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
//        ImageResponse imageResponse = (ImageResponse) message;
//        byte[] bytes1 = getBytes(imageResponse.getImage1());
//        byte[] bytes2 = getBytes(imageResponse.getImage2());
//        int capacity = bytes1.length + bytes2.length + 8;
//        IoBuffer buffer = IoBuffer.allocate(capacity, false);
//        buffer.putInt(bytes1.length);
//        buffer.put(bytes1);
//        buffer.putInt(bytes2.length);
//        buffer.put(bytes2);
//        buffer.flip();
//        out.write(buffer);
//    }

//    private byte[] getBytes(BufferedImage image) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(image, "PNG", baos);
//        return baos.toByteArray();
//    }

	@Override
	protected void encodeBody(IoSession session, T message, IoBuffer out) {
		
		Charset charset = Charset.forName("UTF-8");
		CharsetEncoder ce = charset.newEncoder();
		
		try {
			out.putString(message.getXmlStr(), ce);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
	}
	
	public void dispose(IoSession session) throws Exception {
        // nothing to dispose
    }
}
