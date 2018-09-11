package com.hhly.ticket.service.ticket.dealer.shenfu;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.shenfu.request.RequestOrder;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 获取余额
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class CathecticRequestMsg extends AbstractXml {

	private List<RequestOrder> orderlist;

	public List<RequestOrder> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<RequestOrder> orderlist) {
		this.orderlist = orderlist;
	}

}
