package com.hhly.ticket.service.ticket.dealer.hongzhan.response;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.hongzhan.HongZhanUtil;

public class CheckTicket implements ICheckTicket {

	private String id;

	private String status;

	private String spinfo;

	private String ticketid;

	private String tickettime;

	private String failreason;

	@Override
	public String getBatchNum() {
		return id;
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return ticketid;
	}

	@Override
	public String getThirdNum() {
		return null;
	}

	@Override
	public String getReceiptContent() {
		return null;
	}

	@Override
	public String getOdd() {
		return spinfo;
	}

	@Override
	public String getCode() {
		return status;
	}

	@Override
	public String getMessage() {
		return failreason + ":" + HongZhanUtil.getErrorMessage(failreason);
	}

	@Override
	public boolean isOutSuccess() {
		return "2".equals(getCode());
	}

	@Override
	public boolean isOutFial() {
		return !("0".equals(getCode()) || "2".equals(getCode()) || "5".equals(getCode()));
	}

	@Override
	public boolean isNotExist() {
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSpinfo() {
		return spinfo;
	}

	public void setSpinfo(String spinfo) {
		this.spinfo = spinfo;
	}

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}

	public String getTickettime() {
		return tickettime;
	}

	public void setTickettime(String tickettime) {
		this.tickettime = tickettime;
	}

	public String getFailreason() {
		return failreason;
	}

	public void setFailreason(String failreason) {
		this.failreason = failreason;
	}

}
