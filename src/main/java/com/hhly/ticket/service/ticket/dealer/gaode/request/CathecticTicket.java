package com.hhly.ticket.service.ticket.dealer.gaode.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ticket")
public class CathecticTicket {
	
     private String ticketId;
     
     private String passMode;
     
     private String chips;
     
     private String  multiple;
     
     private String money;
     
     private String  betContents;
     
     private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getPassMode() {
		return passMode;
	}

	public void setPassMode(String passMode) {
		this.passMode = passMode;
	}

	public String getChips() {
		return chips;
	}

	public void setChips(String chips) {
		this.chips = chips;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBetContents() {
		return betContents;
	}

	public void setBetContents(String betContents) {
		this.betContents = betContents;
	}
     
     
     
}
