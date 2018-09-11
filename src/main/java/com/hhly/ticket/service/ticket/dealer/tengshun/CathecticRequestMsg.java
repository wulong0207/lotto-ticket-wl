package com.hhly.ticket.service.ticket.dealer.tengshun;

import com.hhly.ticket.service.ticket.dealer.tengshun.request.RqCathecticBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注
 * @author jiangwei
 * @date 2018年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("request")
public class CathecticRequestMsg extends AbstractMsg {
	
	protected static String PREFIX_CDATA    = "<![CDATA[";
	
	protected static String SUFFIX_CDATA    = "]]>";
	
	public CathecticRequestMsg(String sid, String agent) {
		super(sid, agent);
	}

	private RqCathecticBody body;
	
	

	@Override
	public String toXml() {
		return super.toXml();
	}

	/**
	 * @return the body
	 */
	public RqCathecticBody getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(RqCathecticBody body) {
		this.body = body;
	}

}
