package com.hhly.ticket.service.ticket.dealer.tengshun;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.tengshun.response.NotifyTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("response")
public class NotifyResponseMsg extends AbstractXml {
    
	@XStreamImplicit
	private List<NotifyTicket> tickets;

	/**
	 * @return the tickets
	 */
	public List<NotifyTicket> getTickets() {
		return tickets;
	}

	/**
	 * @param tickets
	 *            the tickets to set
	 */
	public void setTickets(List<NotifyTicket> tickets) {
		this.tickets = tickets;
	}

}
