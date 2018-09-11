package com.hhly.ticket.service.ticket.dealer.ruilang.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class CheckOrderResponse {
	
	@XStreamAsAttribute
    private String errorcode;
	
	@XStreamImplicit
	private List<CheckOrder> order;

	
	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public List<CheckOrder> getOrder() {
		return order;
	}

	public void setOrder(List<CheckOrder> order) {
		this.order = order;
	}
	
	
	
}
