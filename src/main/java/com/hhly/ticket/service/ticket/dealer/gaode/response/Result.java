package com.hhly.ticket.service.ticket.dealer.gaode.response;

import java.util.List;

public class Result {
	
    private String account;
    
    private List<Ticket> tickets;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
    
    
}
