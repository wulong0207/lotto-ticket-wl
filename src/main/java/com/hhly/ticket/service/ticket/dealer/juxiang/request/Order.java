package com.hhly.ticket.service.ticket.dealer.juxiang.request;

/**
 * 订单pojo
 * @author zc
 *
 */
public class Order {

	private String orderId;
	
	private long timeStamp;
	
	private String ticketMoney;
	
	private int betCount;
	
	private String betDetail;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTicketMoney() {
		return ticketMoney;
	}

	public void setTicketMoney(String ticketMoney) {
		this.ticketMoney = ticketMoney;
	}

	public int getBetCount() {
		return betCount;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	public String getBetDetail() {
		return betDetail;
	}

	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
}
