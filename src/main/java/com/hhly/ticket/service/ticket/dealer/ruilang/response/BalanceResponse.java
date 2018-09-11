package com.hhly.ticket.service.ticket.dealer.ruilang.response;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class BalanceResponse {
	
	@XStreamAsAttribute
    private String errorcode;
	
	private String allBalance;

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getAllBalance() {
		return allBalance;
	}

	public void setAllBalance(String allBalance) {
		this.allBalance = allBalance;
	}

	
	
	
}
