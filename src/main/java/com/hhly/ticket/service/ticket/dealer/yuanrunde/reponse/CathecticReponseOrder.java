package com.hhly.ticket.service.ticket.dealer.yuanrunde.reponse;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")
public class CathecticReponseOrder {
	public String id;
	
	public String sysid;
	
	public String errorCode; 	
	
	public String msg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
