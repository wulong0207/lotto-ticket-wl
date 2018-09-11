package com.hhly.ticket.service.ticket.dealer.zongguan.response;

import java.util.List;

public class CathecticBody extends Body{
	
	private List<ResponseTicket> tickets;

	public List<ResponseTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<ResponseTicket> tickets) {
		this.tickets = tickets;
	}
}
