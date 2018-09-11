package com.hhly.ticket.service.ticket.dealer.hengpeng.response;

import com.hhly.ticket.service.ticket.dealer.hengpeng.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("body")
public class Body extends AbstractXml{
	
	private Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
