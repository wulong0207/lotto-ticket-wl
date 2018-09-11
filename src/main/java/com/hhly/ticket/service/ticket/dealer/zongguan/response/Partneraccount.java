package com.hhly.ticket.service.ticket.dealer.zongguan.response;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Partneraccount {
	
	@XStreamAsAttribute
    private String balance;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	

	
	   
   
}
