package com.hhly.ticket.service.ticket.dealer.saiwei;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;

public class NotifyTicket implements ICheckResponse{
	
	private List<Ticket> tickets;

	@Override
	public String getCode() {
		return "200";
	}

	@Override
	public String getMessage() {
		return "出票成功";
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return tickets;
	}

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	public boolean isExist() {
		return false;
	}

	/**
	 * @return the tickets
	 */
	public List<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * @param tickets the tickets to set
	 */
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}
