package com.hhly.ticket.base.vo;

import java.util.List;

/**
 * @desc 从新送票
 * @author jiangwei
 * @date 2017年6月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class AgainOutTicketVO {
	/**
	 * 订单号
	 */
	private String orderCode;
	/**
	 * 批次号
	 */
	private String batchNum;
	/**
	 * 票id
	 */
	private String ticket;
	/**
	 * 票ID
	 */
	private List<String> ticketIds;
	/**
	 * 送票类型0 从新送票，1切换出票商
	 */
	private int type;
	/**
	 * 切换出票商渠道
	 */
	private String channelId;
	
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}


	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public List<String> getTicketIds() {
		return ticketIds;
	}

	public void setTicketIds(List<String> ticketIds) {
		this.ticketIds = ticketIds;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public String toString() {
		return "AgainOutTicketVO [orderCode=" + orderCode + ", batchNum=" + batchNum + ", ticket=" + ticket
				+ ", ticketIds=" + ticketIds + ", type=" + type + ", channelId=" + channelId + "]";
	}

}
