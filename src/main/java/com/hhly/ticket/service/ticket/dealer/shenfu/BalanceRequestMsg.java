package com.hhly.ticket.service.ticket.dealer.shenfu;

import com.hhly.ticket.service.ticket.dealer.shenfu.request.BalanceContent;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 获取余额
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class BalanceRequestMsg extends AbstractXml {
	
	private BalanceContent content;

	public BalanceContent getContent() {
		return content;
	}

	public void setContent(BalanceContent content) {
		this.content = content;
	}

}
