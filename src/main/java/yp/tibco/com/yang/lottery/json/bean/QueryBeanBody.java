package yp.tibco.com.yang.lottery.json.bean;

public class QueryBeanBody {
	
	private String TermID;
	private String GameId;

	private String RptType;
	private String Param1;
	private String Param2;

	
	/**
	 * @return the termID
	 */
	public String getTermID() {
		return TermID;
	}
	/**
	 * @param termID the termID to set
	 */
	public void setTermID(String termID) {
		TermID = termID;
	}
	/**
	 * @return the gameId
	 */
	public String getGameId() {
		return GameId;
	}
	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(String gameId) {
		GameId = gameId;
	}
	public String getRptType() {
		return RptType;
	}
	public void setRptType(String rptType) {
		RptType = rptType;
	}
	public String getParam1() {
		return Param1;
	}
	public void setParam1(String param1) {
		Param1 = param1;
	}
	public String getParam2() {
		return Param2;
	}
	public void setParam2(String param2) {
		Param2 = param2;
	}

}
