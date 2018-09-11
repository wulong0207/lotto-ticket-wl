package com.hhly.ticket.service.ticket.dealer.zongguan.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class CheckBody extends Body {
	
    @XStreamImplicit
	private List<CheckTicket> checkTickets;

	/**
	 * @return the checkTickets
	 */
	public List<CheckTicket> getCheckTickets() {
		return checkTickets;
	}

	/**
	 * @param checkTickets
	 *            the checkTickets to set
	 */
	public void setCheckTickets(List<CheckTicket> checkTickets) {
		this.checkTickets = checkTickets;
	}

}
