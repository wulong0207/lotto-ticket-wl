package com.hhly.ticket.service.ticket.dealer.zhongying;

/**
 * @author jiangwei
 * @date 2017年8月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractMsg extends AbstractXml{

	private Head head;


	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}
	
}
