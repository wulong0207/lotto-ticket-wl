package com.hhly.ticket.service.ticket.dealer.zhongying;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.zhongying.request.SearchOrder;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 获取余额
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class SearchRequestMsg extends AbstractXml {

	private List<SearchOrder> orderlist;

	public List<SearchOrder> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<SearchOrder> orderlist) {
		this.orderlist = orderlist;
	}

}
