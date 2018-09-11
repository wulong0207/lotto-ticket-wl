package com.hhly.ticket.service.ticket.dealer.zongguan.response;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ticketresult")
public class CheckTicket implements ICheckTicket {

	@XStreamAsAttribute
	private String lotteryId;
	@XStreamAsAttribute
	private String issueNumber;
	@XStreamAsAttribute
	private String ticketId;
	@XStreamAsAttribute
	private String palmId;
	@XStreamAsAttribute
	private String statusCode;
	@XStreamAsAttribute
	private String message;
	@XStreamAsAttribute
	private String printodd;
	@XStreamAsAttribute
	private String Unprintodd;
	@XStreamAsAttribute
	private String maxBonus;
	@XStreamAsAttribute
	private String printNo;
	@XStreamAsAttribute
	private String PrintOutTime;
	@XStreamAsAttribute
	private String rq;
	@XStreamAsAttribute
	private String ProvinceId;

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getPalmId() {
		return palmId;
	}

	public void setPalmId(String palmId) {
		this.palmId = palmId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPrintodd() {
		return printodd;
	}

	public void setPrintodd(String printodd) {
		this.printodd = printodd;
	}

	public String getUnprintodd() {
		return Unprintodd;
	}

	public void setUnprintodd(String unprintodd) {
		Unprintodd = unprintodd;
	}

	public String getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(String maxBonus) {
		this.maxBonus = maxBonus;
	}

	public String getPrintNo() {
		return printNo;
	}

	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}

	public String getPrintOutTime() {
		return PrintOutTime;
	}

	public void setPrintOutTime(String printOutTime) {
		PrintOutTime = printOutTime;
	}

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public String getProvinceId() {
		return ProvinceId;
	}

	public void setProvinceId(String provinceId) {
		ProvinceId = provinceId;
	}

	@Override
	public String getBatchNum() {
		return getTicketId();
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return getPrintNo();
	}

	@Override
	public String getThirdNum() {
		return null;
	}

	@Override
	public String getReceiptContent() {
		return "";
	}

	@Override
	public String getOdd() {
		return getPrintodd();
	}

	@Override
	public String getCode() {
		return getStatusCode();
	}

	@Override
	public boolean isOutSuccess() {
		return "003".equals(getStatusCode());
	}

	@Override
	public boolean isOutFial() {
		return "004".equals(getStatusCode());
	}

	@Override
	public boolean isNotExist() {
		return "000".equals(getStatusCode());
	}

}
