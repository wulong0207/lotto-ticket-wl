package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.request.Issue;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 彩期请求
 * @author jiangwei
 * @date 2017年5月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class IssueRequestMsg  extends AbstractMsg{
    
	private Issue body;

	public Issue getBody() {
		return body;
	}

	public void setBody(Issue body) {
		this.body = body;
	}
	

}
