package com.hhly.ticket.service.ticket.dealer.yuanrunde;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")
public class Order{
	
	private String id;
	
	private String errorCode;
	
	private List<Ticket> ticketlist;
	
	private String failreason;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<Ticket> getTicketlist() {
		return ticketlist;
	}

	public void setTicketlist(List<Ticket> ticketlist) {
		this.ticketlist = ticketlist;
	}

	public String getFailreason() {
		return failreason;
	}

	public void setFailreason(String failreason) {
		this.failreason = failreason;
	}
	
	
}
