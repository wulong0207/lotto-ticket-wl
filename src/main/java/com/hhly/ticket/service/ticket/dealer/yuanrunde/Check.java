package com.hhly.ticket.service.ticket.dealer.yuanrunde;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;

public class Check implements ICheckResponse{
	private String code;
	
	private List<TicketOdds> ticketOdds; 

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return ticketOdds;
	}

	@Override
	public boolean isSuccess() {
		return "0".equals(code);
	}

	@Override
	public boolean isExist() {
		return "9002".equals(code);
	}

	public List<TicketOdds> getTicketOdds() {
		return ticketOdds;
	}

	public void setTicketOdds(List<TicketOdds> ticketOdds) {
		this.ticketOdds = ticketOdds;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
