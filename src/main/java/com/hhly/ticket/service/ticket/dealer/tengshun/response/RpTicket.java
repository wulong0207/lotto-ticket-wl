package com.hhly.ticket.service.ticket.dealer.tengshun.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ticket")
public class RpTicket {

	@XStreamAsAttribute
	private String desc;
	@XStreamAsAttribute
	private String code;
	@XStreamAsAttribute
	private String apply;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

}
