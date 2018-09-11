package com.hhly.ticket.service.ticket.dealer.yaosen.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ticket")
public class Ticket {
	
	private String ordersID;
	
	private String errorCode;
	
	private String errorMsg;

	public String getOrdersID() {
		return ordersID;
	}

	public void setOrdersID(String ordersID) {
		this.ordersID = ordersID;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	

}
