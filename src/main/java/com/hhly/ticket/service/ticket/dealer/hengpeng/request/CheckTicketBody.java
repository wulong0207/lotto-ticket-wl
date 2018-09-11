package com.hhly.ticket.service.ticket.dealer.hengpeng.request;

import com.hhly.ticket.service.ticket.dealer.hengpeng.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("body")
public class CheckTicketBody extends AbstractXml {

	private CheckTicketQuery ticketQuery;

	public CheckTicketQuery getTicketQuery() {
		return ticketQuery;
	}

	public void setTicketQuery(CheckTicketQuery ticketQuery) {
		this.ticketQuery = ticketQuery;
	}

}
