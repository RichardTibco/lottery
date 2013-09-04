package yp.tibco.com.yang.lottery.json.bean;

public class PurchaseRespBean {
	private Byte TransType;
	private Byte FromID;
	private Integer Message_Length;
	private Integer Status;
	private Integer Sequence_Number;
	private Integer Reserve;
	
	private PurchaseRespBeanBody data;

	
	public PurchaseRespBeanBody getData() {
		return data;
	}
	public void setData(PurchaseRespBeanBody data) {
		this.data = data;
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
	public Integer getMessage_Length() {
		return Message_Length;
	}
	public void setMessage_Length(Integer message_Length) {
		Message_Length = message_Length;
	}
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	public Integer getSequence_Number() {
		return Sequence_Number;
	}
	public void setSequence_Number(Integer sequence_Number) {
		Sequence_Number = sequence_Number;
	}
	public Integer getReserve() {
		return Reserve;
	}
	public void setReserve(Integer reserve) {
		Reserve = reserve;
	}
	
	

}
