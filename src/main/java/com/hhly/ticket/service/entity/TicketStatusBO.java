package com.hhly.ticket.service.entity;

public class TicketStatusBO {
	/**
	 * 订单号
	 */
    private String orderCode;
    /**
     * 状态
     */
    private String status;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TicketStatusBO [orderCode=" + orderCode + ", status=" + status + "]";
	}
}
