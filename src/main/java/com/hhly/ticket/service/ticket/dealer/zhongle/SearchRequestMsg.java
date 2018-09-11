package com.hhly.ticket.service.ticket.dealer.zhongle;

import com.hhly.ticket.service.ticket.dealer.zhongle.request.SearchBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 获取余额
 * @author jiangwei
 * @date 2018年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class SearchRequestMsg extends AbstractMsg {
	

	private SearchBody body;

	public SearchBody getBody() {
		return body;
	}

	public void setBody(SearchBody body) {
		this.body = body;
	}
     
	
	
	
}
