package com.hhly.ticket.service.ticket.dealer.hengpeng.request;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class CheckTicketQuery {
	
	@XStreamImplicit
	private List<CheckTicket> ticket;

	public List<CheckTicket> getTicket() {
		return ticket;
	}

	public void setTicket(List<CheckTicket> ticket) {
		this.ticket = ticket;
	}

}
