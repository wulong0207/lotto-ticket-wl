package com.hhly.ticket.service.ticket.dealer.zhongle.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("order")
public class OrderResponse {
	
	@XStreamAsAttribute
	private String id;

	private String code;

	private String message;

	private String chipMoney;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getChipMoney() {
		return chipMoney;
	}

	public void setChipMoney(String chipMoney) {
		this.chipMoney = chipMoney;
	}
	
	

}
