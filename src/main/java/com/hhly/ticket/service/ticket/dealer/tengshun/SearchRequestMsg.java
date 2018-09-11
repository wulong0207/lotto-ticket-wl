package com.hhly.ticket.service.ticket.dealer.tengshun;

import com.hhly.ticket.service.ticket.dealer.tengshun.request.Query;
import com.hhly.ticket.service.ticket.dealer.tengshun.request.SearchBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 获取余额
 * @author jiangwei
 * @date 2018年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("request")
public class SearchRequestMsg extends AbstractMsg {
	
	
	
	public SearchRequestMsg(String sid, String agent) {
		super(sid, agent);
		body = new SearchBody();
		body.setQuery(new Query());
	}

	private SearchBody body;

	/**
	 * @return the body
	 */
	public SearchBody getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(SearchBody body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		System.out.println(new SearchRequestMsg("1", "2").toXml());
	}
}
