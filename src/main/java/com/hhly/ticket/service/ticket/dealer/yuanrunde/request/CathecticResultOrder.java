package com.hhly.ticket.service.ticket.dealer.yuanrunde.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")
public class CathecticResultOrder {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
