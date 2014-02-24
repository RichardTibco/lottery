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

import org.apache.mina.example.sumup.codec.AddMessageDecoder;
import org.apache.mina.example.sumup.codec.AddMessageEncoder;
import org.apache.mina.example.sumup.codec.ResultMessageDecoder;
import org.apache.mina.example.sumup.codec.ResultMessageEncoder;
import org.apache.mina.example.sumup.message.AddMessage;
import org.apache.mina.example.sumup.message.ResultMessage;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.core.session.IoSession;

import yp.tibco.com.yang.lottery.message.LotteryRequest;
import yp.tibco.com.yang.lottery.message.LotteryResponse;

/**
 * a {@link ProtocolCodecFactory} for the tutorial on how to write a protocol codec
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */

public class LotteryCodecFactory extends DemuxingProtocolCodecFactory {
//    private ProtocolEncoder encoder;
//    private ProtocolDecoder decoder;

    public LotteryCodecFactory(boolean client) {
    	
    	//for test
//        if (client) {
//            encoder = new TestLotteryRequestEncoder();
//            decoder = new LotteryResponseDecoder();
//        } else {
//            encoder = new LotteryResponseEncoder();
//            decoder = new TestLotteryRequestDecoder();
//        }
        
//        if (client) {
//            encoder = new LotteryRequestEncoder();
//            decoder = new LotteryResponseDecoder();
//        } else {
//            encoder = new LotteryResponseEncoder();
//            decoder = new LotteryRequestDecoder();
//        }
    	
    	 if (client) {
    		 super.addMessageEncoder(LotteryRequest.class, LotteryRequestEncoder.class);
             super.addMessageDecoder(LotteryResponseDecoder.class);
         } else // Server
         {
             super.addMessageDecoder(LotteryRequestDecoder.class);
             super.addMessageEncoder(LotteryResponse.class, LotteryResponseEncoder.class);
         }
    	
//    	
//    	 if (server) {
//             super.addMessageDecoder(AddMessageDecoder.class);
//             super.addMessageEncoder(ResultMessage.class, ResultMessageEncoder.class);
//         } else // Client
//         {
//             super.addMessageEncoder(AddMessage.class, AddMessageEncoder.class);
//             super.addMessageDecoder(ResultMessageDecoder.class);
//         }
    }

//    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
//        return encoder;
//    }
//
//    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
//        return decoder;
//    }
}
