package com.hhly.ticket.service.ticket.dealer.hengpeng.response;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class CheckTicketResponse {
	
	@XStreamAsAttribute
    private String code;
	
	
	@XStreamAsAttribute
    private String message;
	
	private CheckTicketQueryResult ticketQueryResult;


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * @return the ticketQueryResult
	 */
	public CheckTicketQueryResult getTicketQueryResult() {
		return ticketQueryResult;
	}


	/**
	 * @param ticketQueryResult the ticketQueryResult to set
	 */
	public void setTicketQueryResult(CheckTicketQueryResult ticketQueryResult) {
		this.ticketQueryResult = ticketQueryResult;
	}
	
	
}
