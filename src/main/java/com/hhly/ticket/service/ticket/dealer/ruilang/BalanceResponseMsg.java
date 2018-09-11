package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.response.BalanceBody;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 余额响应
 * @author jiangwei
 * @date 2017年8月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class BalanceResponseMsg extends AbstractMsg{
	private static XStream  XS = XmlUtil.createXStream(BalanceResponseMsg.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	private  BalanceBody body;

	public BalanceBody getBody() {
		return body;
	}

	public void setBody(BalanceBody body) {
		this.body = body;
	}
	public static void main(String[] args) {
		String string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg v=\"1.0\" id=\"1705182044290100001\"><ctrl><agentID>800213</agentID><cmd>2017</cmd><timestamp>1495111590815</timestamp><md>1d666ca141935b2944e79aa408c9342d</md></ctrl><body><response errorcode=\"0\"><allBalance>1.2</allBalance></response></body></msg>";
		BalanceResponseMsg msg = new BalanceResponseMsg();
		msg = msg.fromXML(string);
		System.out.println(JsonUtil.object2Json(msg));
	}
}
