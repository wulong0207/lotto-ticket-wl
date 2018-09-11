package com.hhly.ticket.service.ticket.dealer.gaode.response;

import com.hhly.ticket.service.ticket.dealer.gaode.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("body")
public class ResponseNotifyBody extends AbstractXml{
	
	public ResponseNotifyBody(String responseCode){
		this.responseCode = responseCode;
	}      
	private String responseCode;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	
      
}
