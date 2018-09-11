package com.hhly.ticket.service.ticket.dealer.zhongle.request;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.gaode.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * @desc 投注body
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("body")
public class CathecticBody extends AbstractXml {
	
	@XStreamImplicit
	private  List<Order> order;

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}
	
	
	
}
