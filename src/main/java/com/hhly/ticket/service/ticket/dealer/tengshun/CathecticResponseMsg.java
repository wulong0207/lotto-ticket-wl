package com.hhly.ticket.service.ticket.dealer.tengshun;

import com.hhly.ticket.service.ticket.dealer.tengshun.response.RpCathecticBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注相应
 * @author jiangwei
 * @date 2018年3月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("response")
public class CathecticResponseMsg extends AbstractMsg {
	
	private static XStream XS = XmlUtil.createXStream(CathecticResponseMsg.class);

	
	private RpCathecticBody body;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}


	public RpCathecticBody getBody() {
		return body;
	}


	public void setBody(RpCathecticBody body) {
		this.body = body;
	}


	

	
}
