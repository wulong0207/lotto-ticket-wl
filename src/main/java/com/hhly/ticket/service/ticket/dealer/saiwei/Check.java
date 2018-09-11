package com.hhly.ticket.service.ticket.dealer.saiwei;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;

public class Check implements ICheckResponse{
	private String code;

	private String message;

	private Data data;

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

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return data.getTickets();
	}

	@Override
	public boolean isSuccess() {
		return "200".equals(code);
	}

	@Override
	public boolean isExist() {
		return false;
	}

}
