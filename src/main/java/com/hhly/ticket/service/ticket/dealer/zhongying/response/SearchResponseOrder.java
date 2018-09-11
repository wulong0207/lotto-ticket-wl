package com.hhly.ticket.service.ticket.dealer.zhongying.response;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")
public class SearchResponseOrder implements ICheckTicket {
	private String orderid;
	private String failreason;
	private String errorcode;
	private String sysid;
	private String printtime;
	private List<SearchTicket> ticketlist;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getFailreason() {
		return failreason;
	}

	public void setFailreason(String failreason) {
		this.failreason = failreason;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public List<SearchTicket> getTicketlist() {
		return ticketlist;
	}

	public void setTicketlist(List<SearchTicket> ticketlist) {
		this.ticketlist = ticketlist;
	}

	@Override
	public String getBatchNum() {
		return orderid;
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return ticketlist.get(0).getExternalid();
	}

	@Override
	public String getThirdNum() {
		return null;
	}

	@Override
	public String getReceiptContent() {
		return null;
	}

	@Override
	public String getOdd() {
		return ticketlist.get(0).getSp();
	}

	@Override
	public String getCode() {
		return errorcode;
	}

	@Override
	public String getMessage() {
		return failreason;
	}

	@Override
	public boolean isOutSuccess() {
		return "0".equals(errorcode);
	}

	@Override
	public boolean isOutFial() {
		return "2".equals(errorcode);
	}

	@Override
	public boolean isNotExist() {
		return "5".equals(errorcode);
	}

	/**
	 * @return the sysid
	 */
	public String getSysid() {
		return sysid;
	}

	/**
	 * @param sysid the sysid to set
	 */
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	/**
	 * @return the printtime
	 */
	public String getPrinttime() {
		return printtime;
	}

	/**
	 * @param printtime the printtime to set
	 */
	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}

}
