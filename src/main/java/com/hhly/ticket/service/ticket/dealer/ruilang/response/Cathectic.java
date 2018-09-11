package com.hhly.ticket.service.ticket.dealer.ruilang.response;

import com.hhly.ticket.service.ticket.dealer.ruilang.request.CathecticOrder;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @desc 响应
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Cathectic {
	
	@XStreamAsAttribute
    private String errorcode;
	
	private CathecticOrder order;

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	/**
	 * @return the order
	 */
	public CathecticOrder getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(CathecticOrder order) {
		this.order = order;
	}
}
