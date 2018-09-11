package com.hhly.ticket.service.ticket.dealer.zongguan.request;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class CheckBody {
	
	@XStreamImplicit
	private List<QueryTicket> queryTickets;
	

	public List<QueryTicket> getQueryTickets() {
		return queryTickets;
	}

	public void setQueryTickets(List<QueryTicket> queryTickets) {
		this.queryTickets = queryTickets;
	}
	
}
