package com.hhly.ticket.service.entity;

import java.util.List;

/**
 * @desc 订单信息
 * @author jiangwei
 * @date 2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class OrderBO {
	/**
	 * 主键id
	 */
	private int id;
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * 彩种code
	 */
	private int lotteryCode;
	/**
	 * 彩期
	 */
	private String lotteryIssue;
	/**
	 * 订单总额
	 */
	private double orderAmount;
	/**
	 * 是否允许检票
	 */
	private  int checkTicket;
	/**
	 * 订单彩票总数
	 */
	private int splitNum;
	/**
	 * 票信息
	 */
	private List<TicketBO> ticketBOs;

	public int getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(int lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getLotteryIssue() {
		return lotteryIssue;
	}

	public void setLotteryIssue(String lotteryIssue) {
		this.lotteryIssue = lotteryIssue;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getCheckTicket() {
		return checkTicket;
	}

	public void setCheckTicket(int checkTicket) {
		this.checkTicket = checkTicket;
	}

	public int getSplitNum() {
		return splitNum;
	}

	public void setSplitNum(int splitNum) {
		this.splitNum = splitNum;
	}

	public List<TicketBO> getTicketBOs() {
		return ticketBOs;
	}

	public void setTicketBOs(List<TicketBO> ticketBOs) {
		this.ticketBOs = ticketBOs;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
