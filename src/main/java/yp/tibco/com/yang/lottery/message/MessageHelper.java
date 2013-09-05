package yp.tibco.com.yang.lottery.message;

public class MessageHelper {

	private Byte TransType;
	private Byte FromID;
	private Short MessageLength;
	private int Status;
	private int SequenceNumber;
	private int Reserve;
	private String content;
	
	
	public MessageHelper(){}
	
	public void setStringToMessage(String inputString){
		inputString = inputString.substring(inputString.indexOf("=")+1).trim();
		int attSize = inputString.split(",").length;
		for(int i=0; i<attSize; i++){
			String attribute = inputString.split(",")[i];
			if(attribute.split(":")[0].trim().equals("TransType")){
				this.TransType = Byte.parseByte(attribute.split(":")[1].trim());
			}
			if(attribute.split(":")[0].trim().equals("FromID")){
				this.FromID = Byte.parseByte(attribute.split(":")[1].trim());
			}
			if(attribute.split(":")[0].trim().equals("MessageLength")){
				this.MessageLength = Short.parseShort(attribute.split(":")[1].trim());
			}
			if(attribute.split(":")[0].trim().equals("Status")){
				this.Status = Integer.parseInt(attribute.split(":")[1].trim());
			}
			if(attribute.split(":")[0].trim().equals("SequenceNumber")){
				this.SequenceNumber = Integer.parseInt(attribute.split(":")[1].trim());
			}
			if(attribute.split(":")[0].trim().equals("Message content")){
				this.content = attribute.split(":")[1];
			}
		}
	}

	/**
	 * @return the transType
	 */
	public Byte getTransType() {
		return TransType;
	}

	/**
	 * @param transType the transType to set
	 */
	public void setTransType(Byte transType) {
		TransType = transType;
	}

	/**
	 * @return the fromID
	 */
	public Byte getFromID() {
		return FromID;
	}

	/**
	 * @param fromID the fromID to set
	 */
	public void setFromID(Byte fromID) {
		FromID = fromID;
	}

	/**
	 * @return the messageLength
	 */
	public Short getMessageLength() {
		return MessageLength;
	}

	/**
	 * @param messageLength the messageLength to set
	 */
	public void setMessageLength(Short messageLength) {
		MessageLength = messageLength;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return Status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		Status = status;
	}

	/**
	 * @return the sequenceNumber
	 */
	public int getSequenceNumber() {
		return SequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		SequenceNumber = sequenceNumber;
	}

	/**
	 * @return the reserve
	 */
	public int getReserve() {
		return Reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(int reserve) {
		Reserve = reserve;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	


//	public static void main(String[] args){
//		String testStr = "response is =TransType : 1,FromID : 1,MessageLength : 592,Status : 22,SequenceNumber : 4,Message content :<?xml version='1.0' encoding='gb2312' ?><webinf><GameName>B001</GameName><SaleName>shuangseqiu</SaleName><GameType>2</GameType><GameId>2</GameId><BetPerTick>5</BetPerTick><SpecialNum>1</SpecialNum><NumPerBet>7</NumPerBet><MultiDraw>0</MultiDraw><MultiFlag>50</MultiFlag><SalePerm>3</SalePerm><MoneyPerBet>200</MoneyPerBet><ShowDraw>2014029</ShowDraw><StartTime>20140317073000</StartTime><EndTime>20140318200000</EndTime><MinDan>1</MinDan><MaxDan>5</MaxDan><MinTuo>2</MinTuo><MaxTuo>32</MaxTuo><CmaxNum>33</CmaxNum><CminNum>1</CminNum><CSpMaxNum>16</CSpMaxNum><CSpMinNum>1</CSpMinNum></webinf>";
//		MessageHelper mh = new MessageHelper();
//		mh.setStringToMessage(testStr);
//		mh.getContent();
//	}
}
