package com.hhly.ticket.service.ticket.dealer.yuanrunde;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ticket")
public class Ticket{
	
	@XStreamAsAttribute
	private String plv;

	@XStreamAsAttribute
	private String printtime;

	public String getPlv() {
		return plv;
	}

	public void setPlv(String plv) {
		this.plv = plv;
	}

	public String getPrinttime() {
		return printtime;
	}

	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}

}
