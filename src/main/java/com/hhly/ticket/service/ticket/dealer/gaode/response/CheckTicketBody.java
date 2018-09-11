package com.hhly.ticket.service.ticket.dealer.gaode.response;

import java.util.List;

public class CheckTicketBody extends ResponseBody {
	
	private List<CheckTicket> tickets;

	public List<CheckTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<CheckTicket> tickets) {
		this.tickets = tickets;
	}
}
