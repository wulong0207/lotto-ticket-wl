package com.hhly.ticket.service.ticket.dealer.tengshun.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ticket")
public class NotifyTicket extends Row {

	@XStreamAsAttribute
	private String bid;
	@XStreamAsAttribute
	private String code;
	@XStreamAsAttribute
	private String desc;
	
	@Override
	public boolean isOutSuccess() {
		return "0".equals(getCode());
	}

	@Override
	public boolean isOutFial() {
		return !isOutSuccess();
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
