package com.hhly.ticket.service.ticket.dealer.gaode;

import com.hhly.ticket.service.ticket.dealer.gaode.response.BalanceBody;
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
@XStreamAlias("message")
public class BalanceResponseMsg extends AbstractMsg{
	
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
	
	public static void main(String[] args) {
		String string = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<message version=\"1.0\">"+ 
						  "<header> "+
						    "<timestamp>时间戳</timestamp>  "+
						    "<transactionType>操作编码</transactionType>"+  
						    "<digest>消息摘要</digest>  "+
						    "<userName>通道商用户名</userName>"+ 
						  "</header>"+  
						  "<body> "+
						    "<account>账户余额</account>"+ 
						  "</body> "+ 
						"</message>";
		BalanceResponseMsg msg = new BalanceResponseMsg().fromXML(string);
		System.out.println(msg.toXml());
	}
	
}
