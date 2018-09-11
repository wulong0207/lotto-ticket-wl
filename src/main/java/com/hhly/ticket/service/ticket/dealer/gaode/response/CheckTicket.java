package com.hhly.ticket.service.ticket.dealer.gaode.response;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ticket")
public class CheckTicket  implements ICheckTicket{
	
	private String ticketId;
	
	private String status;
	
	private String time;
	
	private String TCid;
	
	private String odd;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTCid() {
		return TCid;
	}

	public void setTCid(String tCid) {
		TCid = tCid;
	}

	/**
	 * @return the odd
	 */
	public String getOdd() {
		return odd;
	}

	/**
	 * @param odd the odd to set
	 */
	public void setOdd(String odd) {
		this.odd = odd;
	}

	@Override
	public String getBatchNum() {
		return ticketId;
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return TCid;
	}

	@Override
	public String getReceiptContent() {
		return "";
	}

	@Override
	public boolean isOutSuccess() {
		return "2".equals(status);
	}

	@Override
	public boolean isOutFial() {
		return "3".equals(status);
	}

	@Override
	public String getThirdNum() {
		return null;
	}

	@Override
	public String getCode() {
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public boolean isNotExist() {
		return "0".equals(status);
	}

}
