package com.hhly.ticket.service.ticket.dealer.gaode.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ticket")
public class Ticket {
	
	private String ticketId;
	
	private String ticketResultCode;
	
	private String ticketResultMessage;
	
	private String orderID;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketResultCode() {
		return ticketResultCode;
	}

	public void setTicketResultCode(String ticketResultCode) {
		this.ticketResultCode = ticketResultCode;
	}

	public String getTicketResultMessage() {
		return ticketResultMessage;
	}

	public void setTicketResultMessage(String ticketResultMessage) {
		this.ticketResultMessage = ticketResultMessage;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

}
