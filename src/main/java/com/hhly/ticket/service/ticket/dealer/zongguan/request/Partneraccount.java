package com.hhly.ticket.service.ticket.dealer.zongguan.request;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Partneraccount {
	
	@XStreamAsAttribute
    private String partnerid;
	

	public Partneraccount(String partnerid) {
		super();
		this.partnerid = partnerid;
	}

	public String getPartnerid() {
		return partnerid;
	}
	
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	   
   
}
