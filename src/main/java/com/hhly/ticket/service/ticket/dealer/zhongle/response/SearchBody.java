package com.hhly.ticket.service.ticket.dealer.zhongle.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SearchBody {
	
	@XStreamImplicit
	private List<Order> orders;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	
	
}
