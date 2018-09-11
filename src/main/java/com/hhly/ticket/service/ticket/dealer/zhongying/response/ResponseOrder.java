package com.hhly.ticket.service.ticket.dealer.zhongying.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")
public class ResponseOrder {
	private String orderid;
	private String sysid;
	private String errorcode;
	private String msg;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
