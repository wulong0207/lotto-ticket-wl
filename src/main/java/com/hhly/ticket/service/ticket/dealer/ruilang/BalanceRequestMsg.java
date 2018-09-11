package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.request.Balance;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 余额获取
 * @author jiangwei
 * @date 2017年8月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class BalanceRequestMsg extends AbstractMsg{
	/**
	 * 消息体
	 */
	private Balance body;

	public Balance getBody() {
		return body;
	}

	public void setBody(Balance body) {
		this.body = body;
	}
	
	
}
