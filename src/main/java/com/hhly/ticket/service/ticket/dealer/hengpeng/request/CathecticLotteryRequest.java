package com.hhly.ticket.service.ticket.dealer.hengpeng.request;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class CathecticLotteryRequest {
	
	@XStreamImplicit
    private List<CathecticTicket> ticket;

	/**
	 * @return the ticket
	 */
	public List<CathecticTicket> getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(List<CathecticTicket> ticket) {
		this.ticket = ticket;
	}
}
