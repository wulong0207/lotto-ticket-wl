package com.hhly.ticket.service.ticket.dealer.zongguan.request;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class TicketOrder {
	
	@XStreamAsAttribute
    private String lotteryId;
	@XStreamAsAttribute
    private String ticketsnum;
	@XStreamAsAttribute
    private String totalmoney;
    
    private List<RequestTicket> tickets;

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getTicketsnum() {
		return ticketsnum;
	}

	public void setTicketsnum(String ticketsnum) {
		this.ticketsnum = ticketsnum;
	}

	public String getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(String totalmoney) {
		this.totalmoney = totalmoney;
	}

	public List<RequestTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<RequestTicket> tickets) {
		this.tickets = tickets;
	}
    
    
    
}
