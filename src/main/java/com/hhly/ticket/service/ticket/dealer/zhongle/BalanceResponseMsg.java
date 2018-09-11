package com.hhly.ticket.service.ticket.dealer.zhongle;

import com.hhly.ticket.service.ticket.dealer.zhongle.response.BalanceBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 账号余额响应
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("TransResult")
public class BalanceResponseMsg extends AbstractMsg {

	private static XStream XS = XmlUtil.createXStream(BalanceResponseMsg.class);

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
