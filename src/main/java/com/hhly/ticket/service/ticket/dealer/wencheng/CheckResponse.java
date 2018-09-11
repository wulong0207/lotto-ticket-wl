package com.hhly.ticket.service.ticket.dealer.wencheng;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;

public class CheckResponse implements ICheckResponse {

	private String errorcode;

	private String errormsg;

	private List<Ticket> data;

	@Override
	public String getCode() {
		return errorcode;
	}

	@Override
	public String getMessage() {
		return errormsg;
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return data;
	}

	@Override
	public boolean isSuccess() {
		return "1000".equals(errorcode);
	}

	@Override
	public boolean isExist() {
		return false;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public List<Ticket> getData() {
		return data;
	}

	public void setData(List<Ticket> data) {
		this.data = data;
	}

}
