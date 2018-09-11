package com.hhly.ticket.service.ticket.dealer.hengpeng.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ticket")
public class CheckTicket {
	
	@XStreamAsAttribute
	private String id;

	
	public CheckTicket(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
