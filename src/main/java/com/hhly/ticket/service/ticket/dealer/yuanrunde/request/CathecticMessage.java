package com.hhly.ticket.service.ticket.dealer.yuanrunde.request;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.yuanrunde.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class CathecticMessage extends AbstractXml {
	
	private List<CathecticOrder> orders;

	public List<CathecticOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<CathecticOrder> orders) {
		this.orders = orders;
	}

	
	
	
}
