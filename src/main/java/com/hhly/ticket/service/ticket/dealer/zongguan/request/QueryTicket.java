package com.hhly.ticket.service.ticket.dealer.zongguan.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("queryticket")
public class QueryTicket {
	
	@XStreamAsAttribute
    private String ticketId;
 
	
	public QueryTicket(String ticketId) {
		super();
		this.ticketId = ticketId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
    
    
}
