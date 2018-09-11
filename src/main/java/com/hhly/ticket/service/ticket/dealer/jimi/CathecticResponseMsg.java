package com.hhly.ticket.service.ticket.dealer.jimi;

import com.hhly.ticket.service.ticket.dealer.jimi.response.CathecticTicketBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注响应
 * @author jiangwei
 * @date 2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class CathecticResponseMsg extends AbstractMsg {
	private static XStream XS = XmlUtil.createXStream(CathecticResponseMsg.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	private CathecticTicketBody body;

	public CathecticTicketBody getBody() {
		return body;
	}

	public void setBody(CathecticTicketBody body) {
		this.body = body;
	}

}
