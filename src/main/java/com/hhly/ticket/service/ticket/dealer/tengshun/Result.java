package com.hhly.ticket.service.ticket.dealer.tengshun;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Result {
	
	@XStreamAsAttribute
	private String code;
	@XStreamAsAttribute
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
