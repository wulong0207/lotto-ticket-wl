package com.hhly.ticket.service.ticket.dealer.zongguan.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ticket")
public class ResponseTicket {
	@XStreamAsAttribute
	private String ticketId;
	@XStreamAsAttribute
	private String multiple;
	@XStreamAsAttribute
	private String issueNumber;
	@XStreamAsAttribute
	private String betType;
	@XStreamAsAttribute
	private String betMoney;
	@XStreamAsAttribute
	private String betUnits;
	@XStreamAsAttribute
	private String statusCode;
	@XStreamAsAttribute
	private String message;
	@XStreamAsAttribute
	private String palmid;
	@XStreamAsAttribute
	private String detailmessage;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public String getBetMoney() {
		return betMoney;
	}

	public void setBetMoney(String betMoney) {
		this.betMoney = betMoney;
	}

	public String getBetUnits() {
		return betUnits;
	}

	public void setBetUnits(String betUnits) {
		this.betUnits = betUnits;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPalmid() {
		return palmid;
	}

	public void setPalmid(String palmid) {
		this.palmid = palmid;
	}

	public String getDetailmessage() {
		return detailmessage;
	}

	public void setDetailmessage(String detailmessage) {
		this.detailmessage = detailmessage;
	}

}
