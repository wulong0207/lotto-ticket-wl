package com.hhly.ticket.service.ticket.dealer.ruilang.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("order")
public class CheckOrder {
	@XStreamAsAttribute
	private String merchantid;
	@XStreamAsAttribute
	private String orderno;
	@XStreamAsAttribute
	private String issue;
	@XStreamAsAttribute
	private String lotoid;
	@XStreamAsAttribute
	private String status;
	
	@XStreamImplicit
	private List<CheckOrderTicket> ticket;

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getLotoid() {
		return lotoid;
	}

	public void setLotoid(String lotoid) {
		this.lotoid = lotoid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the ticket
	 */
	public List<CheckOrderTicket> getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(List<CheckOrderTicket> ticket) {
		this.ticket = ticket;
	}

}
