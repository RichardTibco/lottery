/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package yp.tibco.com.yang.lottery.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.example.sumup.message.AbstractMessage;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import yp.tibco.com.yang.lottery.message.HeaderMessage;
import yp.tibco.com.yang.lottery.message.LotteryRequest;

/**
 * A {@link MessageDecoder} that decodes message header and forwards
 * the decoding of body to a subclass.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public abstract class AbstractMessageDecoder implements MessageDecoder {
    
	protected Byte TransType;
    
	protected Byte FromID;
	
	protected Short MessageLength;
	
	protected int Status;
	
	protected int SequenceNumber;
	
	protected int Reserve;
	

    private boolean readHeader;

//    protected AbstractMessageDecoder(int type) {
//        this.type = type;
//    }
    
    protected AbstractMessageDecoder() {
    	
    }

    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
    	
    	
        // Return NEED_DATA if the whole header is not read yet.
        if (in.remaining() < Constants.HEADER_LEN) {
            return MessageDecoderResult.NEED_DATA;
        }

        // Return OK if type and bodyLength matches.
//        if (TransType == in.get()) {
//            return MessageDecoderResult.OK;
//        }

        // Return NOT_OK if not matches.
//        return MessageDecoderResult.NOT_OK;
        return MessageDecoderResult.OK;
    }

    public MessageDecoderResult decode(IoSession session, IoBuffer in,
            ProtocolDecoderOutput out) throws Exception {
        // Try to skip header if not read.
        if (!readHeader) {
            
            TransType = in.get();
        	FromID = in.get();
        	MessageLength = in.getShort();
        	Status = in.getInt();
        	SequenceNumber = in.getInt();
        	Reserve = in.getInt();
            
            readHeader = true;
        }

        // Try to decode body
        HeaderMessage m = decodeBody(session, in);
        // Return NEED_DATA if the body is not fully read.
        if (m == null) {
            return MessageDecoderResult.NEED_DATA;
        } else {
        	m.setTransType(TransType);
        	m.setFromID(FromID);
        	m.setMessageLength(MessageLength);
        	m.setStatus(Status);
        	m.setSequenceNumber(SequenceNumber);
        	m.setReserve(Reserve);
            readHeader = false; // reset readHeader for the next decode
        }
//        m.setSequence(sequence);
        out.write(m);

        return MessageDecoderResult.OK;
    }

    /**
     * @return <tt>null</tt> if the whole body is not read yet
     */
    protected abstract HeaderMessage decodeBody(IoSession session,IoBuffer in);
}
