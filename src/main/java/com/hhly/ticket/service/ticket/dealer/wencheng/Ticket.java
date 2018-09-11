package com.hhly.ticket.service.ticket.dealer.wencheng;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;

public class Ticket implements ICheckTicket {
	private String big;
	private String binfo;
	private String bonus;
	private String issue;
	private String lotid;
	private String lotres;
	private String status;
	private String statusdesc;
	private String tax;
	private String tscn;

	public String getBig() {
		return big;
	}

	public void setBig(String big) {
		this.big = big;
	}

	public String getBinfo() {
		return binfo;
	}

	public void setBinfo(String binfo) {
		this.binfo = binfo;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getLotid() {
		return lotid;
	}

	public void setLotid(String lotid) {
		this.lotid = lotid;
	}

	public String getLotres() {
		return lotres;
	}

	public void setLotres(String lotres) {
		this.lotres = lotres;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusdesc() {
		return statusdesc;
	}

	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTscn() {
		return tscn;
	}

	public void setTscn(String tscn) {
		this.tscn = tscn;
	}

	@Override
	public String getBatchNum() {
		return tscn;
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return null;
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
		return lotres;
	}

	@Override
	public String getCode() {
		return status;
	}

	@Override
	public String getMessage() {
		return statusdesc;
	}

	@Override
	public boolean isOutSuccess() {
		return "2".equals(status);
	}

	@Override
	public boolean isOutFial() {
		return "3".equals(status) || "4".equals(status) || "5".equals(status);
	}

	@Override
	public boolean isNotExist() {
		return false;
	}

}
