package com.hhly.ticket.service.ticket.dealer.hengpeng.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ticket")
public class NotifyTicket {
	
	@XStreamAsAttribute
	private String id;
	@XStreamAsAttribute
	private String dealTime;
	@XStreamAsAttribute
	private String status;
	@XStreamAsAttribute
	private String message;
	@XStreamAsAttribute
	private String ticketSerialNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTicketSerialNo() {
		return ticketSerialNo;
	}

	public void setTicketSerialNo(String ticketSerialNo) {
		this.ticketSerialNo = ticketSerialNo;
	}

}
