package com.hhly.ticket.service.ticket.dealer.yaosen;


import com.hhly.ticket.service.ticket.dealer.yaosen.response.BalanceBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 出票商响应
 * @author jiangwei
 * @date 2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class BalanceResponseMsg  extends AbstractMsg{
private static XStream  XS = XmlUtil.createXStream(BalanceResponseMsg.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	private BalanceBody body;

	public BalanceBody getBody() {
		return body;
	}

	public void setBody(BalanceBody body) {
		this.body = body;
	}
	
	
	
}
