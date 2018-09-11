package com.hhly.ticket.service.ticket.dealer.juxiang.request;
/**
 * @desc 检票
 * @author jiangwei
 * @date 2017年9月19日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CheckOrder {
	
    private String orderId;

    
	public CheckOrder(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
