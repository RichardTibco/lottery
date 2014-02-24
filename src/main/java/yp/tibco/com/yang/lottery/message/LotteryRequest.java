package yp.tibco.com.yang.lottery.message;


public class LotteryRequest extends HeaderMessage{
	

	public LotteryRequest() {
	}


	private String xmlStr;
	
	
	public String getXmlStr() {
		return xmlStr;
	}


	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}


	@Override
    public String toString() {
        // it is a good practice to create toString() method on message classes.
        return getTransType() + ":GetParameterRequest(" + xmlStr + ')';
    }
}
