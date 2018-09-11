package com.hhly.ticket.service.ticket.dealer.zhongle.request;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.gaode.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("body")
public class SearchBody extends AbstractXml{
	
	@XStreamImplicit(itemFieldName="OrderId")
	private List<String> orders;

	public List<String> getOrders() {
		return orders;
	}

	public void setOrders(List<String> orders) {
		this.orders = orders;
	}
	
	
}
