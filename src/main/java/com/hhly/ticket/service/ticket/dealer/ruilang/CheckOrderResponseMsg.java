package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.response.CheckOrderBody;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.CheckOrderTicket;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
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
public class CheckOrderResponseMsg extends AbstractMsg{
	
	private static XStream  XS;
	
	static{
		XS= XmlUtil.createXStream(CheckOrderResponseMsg.class);
		XS.registerConverter(new CheckOrderTicket());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	private  CheckOrderBody body;

	public CheckOrderBody getBody() {
		return body;
	}

	public void setBody(CheckOrderBody body) {
		this.body = body;
	}
}
