package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.request.CheckOrderBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 检票请求对象
 * 
 * @desc
 * @author jiangwei
 * @date 2017年5月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class CheckOrderRequestMsg extends AbstractMsg {
	
	private  CheckOrderBody body;

	public CheckOrderBody getBody() {
		return body;
	}

	public void setBody(CheckOrderBody body) {
		this.body = body;
	}
}
