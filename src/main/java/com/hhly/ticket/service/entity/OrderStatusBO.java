package com.hhly.ticket.service.entity;

import java.util.Date;

/**
 * @desc 订单状态
 * @author jiangwei
 * @date 2017年5月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class OrderStatusBO {
	
	private String orderCode;
	
	private int orderStatus;
	
	private Date comeOutTime;
	

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "OrderStatusBO [orderCode=" + orderCode + ", orderStatus=" + orderStatus + "]";
	}

	/**
	 * @return the comeOutTime
	 */
	public Date getComeOutTime() {
		return comeOutTime;
	}

	/**
	 * @param comeOutTime the comeOutTime to set
	 */
	public void setComeOutTime(Date comeOutTime) {
		this.comeOutTime = comeOutTime;
	}
	
	
}
