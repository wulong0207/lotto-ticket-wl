package com.hhly.ticket.service.ticket.dealer.tengshun.request;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Query {
	@XStreamAsAttribute
	private String gid;
	@XStreamAsAttribute
	private String apply;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

}
