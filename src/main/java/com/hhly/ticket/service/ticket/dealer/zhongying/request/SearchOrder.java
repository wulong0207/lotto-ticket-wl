package com.hhly.ticket.service.ticket.dealer.zhongying.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")
public class SearchOrder {

	private String orderid;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

}
