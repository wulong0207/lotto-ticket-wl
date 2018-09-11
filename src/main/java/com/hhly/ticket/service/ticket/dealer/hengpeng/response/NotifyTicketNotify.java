package com.hhly.ticket.service.ticket.dealer.hengpeng.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class NotifyTicketNotify {
	@XStreamImplicit
    private List<NotifyTicket> ticket;

	public List<NotifyTicket> getTicket() {
		return ticket;
	}

	public void setTicket(List<NotifyTicket> ticket) {
		this.ticket = ticket;
	}
    
    
}
