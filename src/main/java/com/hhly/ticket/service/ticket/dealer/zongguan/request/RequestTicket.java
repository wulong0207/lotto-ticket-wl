package com.hhly.ticket.service.ticket.dealer.zongguan.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ticket")
public class RequestTicket {
	@XStreamAsAttribute
	private String ticketId;
	@XStreamAsAttribute
	private String betType;
	@XStreamAsAttribute
	private String issueNumber;
	@XStreamAsAttribute
	private String betUnits;
	@XStreamAsAttribute
	private String multiple;
	@XStreamAsAttribute
	private String betMoney;
	@XStreamAsAttribute
	private String isAppend;

	private String betContent;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getBetUnits() {
		return betUnits;
	}

	public void setBetUnits(String betUnits) {
		this.betUnits = betUnits;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getBetMoney() {
		return betMoney;
	}

	public void setBetMoney(String betMoney) {
		this.betMoney = betMoney;
	}

	public String getIsAppend() {
		return isAppend;
	}

	public void setIsAppend(String isAppend) {
		this.isAppend = isAppend;
	}

	public String getBetContent() {
		return betContent;
	}

	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}

}
