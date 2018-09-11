package com.hhly.ticket.service.ticket.dealer.ruilang.response;

import com.hhly.ticket.service.ticket.dealer.ruilang.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("body")
public class NotifySuccessBody extends AbstractXml {
	
    private String result = "0";

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
    
    
}
