package com.hhly.ticket.service.ticket.dealer.zongguan.response;

import java.util.List;

public class NotifyTicketBody  extends Body{
	
    private List<CheckTicket> ticketresults;

	/**
	 * @return the ticketresults
	 */
	public List<CheckTicket> getTicketresults() {
		return ticketresults;
	}

	/**
	 * @param ticketresults the ticketresults to set
	 */
	public void setTicketresults(List<CheckTicket> ticketresults) {
		this.ticketresults = ticketresults;
	}

}
