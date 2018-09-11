package com.hhly.ticket.service.ticket.dealer.gaode.request;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class CheckTicket {
	
	@XStreamImplicit(itemFieldName="ticketId")
	private List<String> tickets;

	public List<String> getTickets() {
		return tickets;
	}

	public void setTickets(List<String> tickets) {
		this.tickets = tickets;
	}
	
	
}
