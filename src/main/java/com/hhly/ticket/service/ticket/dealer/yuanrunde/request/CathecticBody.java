package com.hhly.ticket.service.ticket.dealer.yuanrunde.request;

import com.hhly.ticket.service.ticket.dealer.yuanrunde.AbstractXml;

public class CathecticBody extends AbstractXml{
	
	private CathecticMessage message;

	public CathecticMessage getMessage() {
		return message;
	}

	public void setMessage(CathecticMessage message) {
		this.message = message;
	}
	
	
}
