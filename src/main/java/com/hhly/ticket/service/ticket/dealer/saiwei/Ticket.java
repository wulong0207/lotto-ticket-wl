package com.hhly.ticket.service.ticket.dealer.saiwei;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.util.JsonUtil;

public class Ticket implements ICheckTicket{
	
	private String lDTicketId;

	private String playingCode;

	private String ticketStatus;

	private String resultMsg;
	
	private String successTime;
	
	private List<Odd> odds;
	
	
	public String getlDTicketId() {
		return lDTicketId;
	}

	public void setlDTicketId(String lDTicketId) {
		this.lDTicketId = lDTicketId;
	}

	public String getPlayingCode() {
		return playingCode;
	}

	public void setPlayingCode(String playingCode) {
		this.playingCode = playingCode;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}


	public List<Odd> getOdds() {
		return odds;
	}

	public void setOdds(List<Odd> odds) {
		this.odds = odds;
	}

	@Override
	public String getBatchNum() {
		return lDTicketId;
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
		return null;
	}

	@Override
	public String getOdd() {
		return JsonUtil.objectList2Json(odds);
	}

	@Override
	public String getCode() {
		return ticketStatus;
	}

	@Override
	public String getMessage() {
		return resultMsg;
	}

	@Override
	public boolean isOutSuccess() {
		return "200003".equals(ticketStatus);
	}

	@Override
	public boolean isOutFial() {
		return "200004".equals(ticketStatus);
	}

	@Override
	public boolean isNotExist() {
		return "200006".equals(ticketStatus);
	}

}
