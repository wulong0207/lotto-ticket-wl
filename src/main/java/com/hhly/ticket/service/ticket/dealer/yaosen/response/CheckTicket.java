package com.hhly.ticket.service.ticket.dealer.yaosen.response;

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

	private String ticketID;

	private String status;

	private String odds;
	
	private String printTime;

	public String getOrdersID() {
		return ordersID;
	}

	public void setOrdersID(String ordersID) {
		this.ordersID = ordersID;
	}

	
	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
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
		return ticketID;
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

}
