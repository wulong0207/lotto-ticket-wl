package com.hhly.ticket.service.ticket.dealer.tengshun.request;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class RqUser {
	
	
	public RqUser(String idcard, String name, String mobile) {
		this.idcard = idcard;
		this.name = name;
		this.mobile = mobile;
	}
	
	

	public RqUser() {
	}



	@XStreamAsAttribute
	private String idcard;
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String mobile;

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
