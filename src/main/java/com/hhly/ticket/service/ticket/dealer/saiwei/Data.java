package com.hhly.ticket.service.ticket.dealer.saiwei;

import java.util.List;

public class Data {
	private Integer balance;

	private String batchId;

	private List<Ticket> tickets;

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	

	

}
