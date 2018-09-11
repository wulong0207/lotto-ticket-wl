package com.hhly.ticket.service.ticket.dealer.yuanrunde;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;

public class TicketOdds implements ICheckTicket{
	
	private String id;
	
	private String errorCode;
	
	private String odds;
	
	private String printtime;

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
		return null;
	}

	@Override
	public String getThirdNum() {
		return null;
	}

	@Override
	public String getReceiptContent() {
		return odds;
	}

	@Override
	public String getOdd() {
		return odds;
	}

	@Override
	public String getCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public boolean isOutSuccess() {
		return "0".equals(errorCode);
	}

	@Override
	public boolean isOutFial() {
		return "2".equals(errorCode);
	}

	@Override
	public boolean isNotExist() {
		return "9002".equals(errorCode);
	}

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

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	public String getPrinttime() {
		return printtime;
	}

	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}
	
	

}
