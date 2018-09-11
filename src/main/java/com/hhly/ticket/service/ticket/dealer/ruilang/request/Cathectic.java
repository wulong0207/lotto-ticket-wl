package com.hhly.ticket.service.ticket.dealer.ruilang.request;

import com.hhly.ticket.service.ticket.dealer.ruilang.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注 body
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("body")
public class Cathectic extends AbstractXml{
	
	private CathecticOrder order;
	
	public CathecticOrder getOrder() {
		return order;
	}

	public void setOrder(CathecticOrder order) {
		this.order = order;
	}
}
