package com.hhly.ticket.service.ticket.dealer.jimi.response;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 检票
 * @author jiangwei
 * @date 2017年10月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("ticket")
public class CheckTicket implements ICheckTicket{
	
	private String ordersID;
	
	private String lotteryID;
    
	private String issue;
	
	private String ticketId;

	private String status;

	private String odds;
	
	private String printTime;
	
	private String charityCode;


	public String getOrdersID() {
		return ordersID;
	}

	public void setOrdersID(String ordersID) {
		this.ordersID = ordersID;
	}

	public String getLotteryID() {
		return lotteryID;
	}

	public void setLotteryID(String lotteryID) {
		this.lotteryID = lotteryID;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	@Override
	public String getBatchNum() {
		return ordersID;
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return ticketId;
	}

	@Override
	public String getReceiptContent() {
		return "";
	}

	@Override
	public String getOdd() {
		return odds;
	}

	@Override
	public boolean isOutSuccess() {
		return "2".equals(status);
	}

	@Override
	public boolean isOutFial() {
		return "-2".equals(status);
	}

	@Override
	public String getThirdNum() {
		return null;
	}

	@Override
	public String getCode() {
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public boolean isNotExist() {
		return false;
	}

	/**
	 * @return the charityCode
	 */
	public String getCharityCode() {
		return charityCode;
	}

	/**
	 * @param charityCode the charityCode to set
	 */
	public void setCharityCode(String charityCode) {
		this.charityCode = charityCode;
	}

}
