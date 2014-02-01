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
package yp.tibco.com.yang.lottery.message;

import java.io.Serializable;

/**
 * A base message for SumUp protocol messages.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class HeaderMessage implements Serializable {
	private Byte TransType;
	private Byte FromID;
	private Short MessageLength;
	private int Status;
	private int SequenceNumber;
	private int Reserve;
	
	
	public HeaderMessage() {
		super();
		// TODO Auto-generated constructor stub
	}


	public HeaderMessage(HeaderMessage h) {
		TransType = h.getTransType();
		FromID = h.getFromID();
		MessageLength = h.getMessageLength();
		Status = h.getStatus();
		SequenceNumber = h.getSequenceNumber();
		Reserve = h.getReserve();
	}


	public Byte getTransType() {
		return TransType;
	}
	public void setTransType(Byte transType) {
		TransType = transType;
	}
	public Byte getFromID() {
		return FromID;
	}
	public void setFromID(Byte fromID) {
		FromID = fromID;
	}
	public Short getMessageLength() {
		return MessageLength;
	}
	public void setMessageLength(Short messageLength) {
		MessageLength = messageLength;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public int getSequenceNumber() {
		return SequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		SequenceNumber = sequenceNumber;
	}
	public int getReserve() {
		return Reserve;
	}
	public void setReserve(int reserve) {
		Reserve = reserve;
	}
}