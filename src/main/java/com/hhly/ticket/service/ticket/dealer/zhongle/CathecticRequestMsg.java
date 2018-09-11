package com.hhly.ticket.service.ticket.dealer.zhongle;

import com.hhly.ticket.service.ticket.dealer.zhongle.request.CathecticBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 北京单场投注请求
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class CathecticRequestMsg extends AbstractMsg {
	
	private CathecticBody body;

	public CathecticBody getBody() {
		return body;
	}

	public void setBody(CathecticBody body) {
		this.body = body;
	}
	
}
