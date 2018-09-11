package com.hhly.ticket.service.ticket.dealer.hengpeng.response;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Response {
	
	@XStreamAsAttribute
    private String code;
	
	
	@XStreamAsAttribute
    private String message;


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
