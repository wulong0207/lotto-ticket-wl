package com.hhly.ticket.service.ticket.dealer.ruilang.request;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ruilang.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("body")
public class CheckOrderBody extends AbstractXml{
	
	@XStreamImplicit
	private List<CheckOrder> order;

	public List<CheckOrder> getOrder() {
		return order;
	}

	public void setOrder(List<CheckOrder> order) {
		this.order = order;
	}
}
