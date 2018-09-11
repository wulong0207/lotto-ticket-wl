package com.hhly.ticket.service.ticket.dealer.people;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;

public class CheckResponse implements ICheckResponse {

	@Override
	public String getCode() {
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return null;
	}

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	public boolean isExist() {
		return false;
	}

}
