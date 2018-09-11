package com.hhly.ticket.service.ticket.dealer.zhongle.response;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import net.sf.json.JSONArray;

@XStreamAlias("order")
public class Order implements ICheckTicket{
	@XStreamAsAttribute
	private String id;
	@XStreamAsAttribute
	private String code;
	@XStreamAsAttribute
	private String message;

	private Ticket ticket;

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

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public String getBatchNum() {
		return id;
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return ticket.getMacid();
	}

	@Override
	public String getThirdNum() {
		return ticket.getId();
	}

	@Override
	public String getReceiptContent() {
		return null;
	}

	@Override
	public String getOdd() {
		return JSONArray.fromObject(ticket.getMatchs()).toString();
	}

	@Override
	public boolean isOutSuccess() {
		return "1".equals(code);
	}

	@Override
	public boolean isOutFial() {
		return "EQ003".equals(code);
	}

	@Override
	public boolean isNotExist() {
		return "EQ001".equals(code);
	}

}
