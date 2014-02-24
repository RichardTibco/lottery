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

public class Constants {
    public static final int TRANSTYPE_LEN = 2;
    
    public static final int FROMID_LEN = 2;
    
    public static final int MESSAGE_LENGTH_LEN = 2;
    
    public static final int STATUS_LEN = 2;

    public static final int SEQUENCE_NUMBER_LEN = 4;
    
    public static final int RESERVE_LEN = 4;

    public static final int HEADER_LEN = TRANSTYPE_LEN + FROMID_LEN + MESSAGE_LENGTH_LEN + STATUS_LEN + SEQUENCE_NUMBER_LEN + RESERVE_LEN;

    public static final int BODY_LEN = 12;

    public static final int RESULT = 0;

    public static final int GET = 1;
    
    public static final int PURCHASE = 2;
    
    public static final int QUERY = 3;

    public static final int RESULT_CODE_LEN = 2;

    public static final int RESULT_VALUE_LEN = 4;

    public static final int ADD_BODY_LEN = 4;

    public static final int RESULT_OK = 0;

    public static final int RESULT_ERROR = 1;

    //TRANS TYPE
    public static final byte TRANS_TYPE_HEARTBEAT = 0;
    
    public static final byte TRANS_TYPE_GET_GAME_PARAM = 1;
    
    public static final byte TRANS_TYPE_NEW_PARAM = 2;

    public static final byte TRANS_TYPE_PURCHASE = 3;

    public static final byte TRANS_TYPE_QUERY = 4;

    public static final byte TRANS_TYPE_QUERY_TICKETS = 5;

    public static final byte TRANS_TYPE_QUERY_BALANCE = 6;

    public static final byte TRANS_TYPE_QUERY_BINGLE_NUMBER = 7;

    public static final byte TRANS_TYPE_QUERY_SALES_REPORT = 8;

    public static final byte TRANS_TYPE_QUERY_PERIOD_FINANCIAL_REPORT = 9;
    
    
    //FROM ID
    public static final byte  FROM_ID_MOBILE_SMS = 1;
    
    public static final byte  FROM_ID_MOBILE_WAP = 2;
    
    public static final byte  FROM_ID_TELEPHONE_IVR = 3;
    
    public static final byte  FROM_ID_INTERNET = 4;
    
    public static final byte  FROM_ID_TV_BOX = 5;
    
    //GAME ID
    
    public static final int GAME_ID_5IN21 = 3;
    
    public static final int GAME_ID_DOUBLE_COLOR = 4;
    
    public static final int GAME_ID_3D = 5;
    
    public static final int GAME_ID_SEVEN_YUE_COLOR = 6;
    
    public static final int GAME_ID_ALL = 99;
    
    //BET TYPE
    	//DOUBLE COLOR
    public static final int BET_TYPE_DOUBLE_COLOR_SINGLE = 0;
    
    public static final int BET_TYPE_DOUBLE_COLOR_MULTIPLE = 1;
    
    public static final int BET_TYPE_DOUBLE_COLOR_COMPLEX = 2;
    
    	//3D
    public static final int BET_3D_COLOR_DIRECT = 0;
    
    public static final int BET_3D_COLOR_PACKAGE = 2;
    
    public static final int BET_3D_COLOR_GROUP = 5;
    
    public static final int BET_3D_COLOR_SINGLE_SELECT_MULTIPLE = 6;
    
    public static final int BET_3D_COLOR_GROUP3_MULTIPLE = 7;
    
    public static final int BET_3D_COLOR_GROUP6_MULTIPLE = 8;
    
    public static final int BET_3D_COLOR_DIRECT_INCLUDE_PACKAGE = 9;
    
    public static final int BET_3D_COLOR_DIRECT_INCLUDE_POINT = 10;
    
    public static final int BET_3D_COLOR_DIRECT_INCLUDE_LINK = 11;
    
    public static final int BET_3D_COLOR_GROUP_INCLUDE_PACKAGE = 12;
    
    public static final int BET_3D_COLOR_GROUP_INCLUDE_POINT = 13;
    
    	//SEVEN YUE COLOR
    
    public static final int BET_SEVEN_YUE_COLOR_SINGLE = 0;
    
    public static final int BET_SEVEN_YUE_COLOR_MULTIPLE = 1;
    
    public static final int BET_SEVEN_YUE_COLOR_COMPLEX = 2;
    
    	//5 IN 21
    
    public static final int BET_5IN21_SINGLE = 0;
    
    public static final int BET_5IN21_MULTIPLE = 1;
    
    public static final int BET_5IN21_COMPLEX = 2;
    
    public static final int BET_5IN21_LUCKY_SINGLE = 50;
    
    public static final int BET_5IN21_LUCKY_MULTIPLE = 51;
    
    private Constants() {
    }
}
