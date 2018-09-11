package com.hhly.ticket.service.ticket.dealer.tengshun.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("ticket")
public class RqTicket {
	
	@XStreamAsAttribute
	private String apply;
	@XStreamAsAttribute
	private String codes;
	@XStreamAsAttribute
	private String mulity;
	@XStreamAsAttribute
	private String money;

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getMulity() {
		return mulity;
	}

	public void setMulity(String mulity) {
		this.mulity = mulity;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

}
