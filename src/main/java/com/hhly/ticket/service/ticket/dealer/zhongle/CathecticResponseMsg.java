package com.hhly.ticket.service.ticket.dealer.zhongle;

import com.hhly.ticket.service.ticket.dealer.zhongle.response.CathecticBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 北京单场投注请求
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("TransResult")
public class CathecticResponseMsg extends AbstractMsg {
	private static XStream XS = XmlUtil.createXStream(CathecticResponseMsg.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	private CathecticBody body;

	public CathecticBody getBody() {
		return body;
	}

	public void setBody(CathecticBody body) {
		this.body = body;
	}

	
}
