 package com.hhly.ticket.service.ticket.dealer.gaode;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.gaode.cathectic.GaoDeDealer;
import com.hhly.ticket.service.ticket.dealer.gaode.response.CheckTicketBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class CheckResponseMsg extends AbstractMsg implements ICheckResponse{

	private static XStream XS = XmlUtil.createXStream(CheckResponseMsg.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	private CheckTicketBody body;
	
	public CheckTicketBody getBody() {
		return body;
	}


	public void setBody(CheckTicketBody body) {
		this.body = body;
	}



	public static void main(String[] args) {
		String string = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<message version=\"1.0\">" + "<header> "
				+ "<timestamp>时间戳</timestamp>  " + "<transactionType>操作编码</transactionType>" + "<digest>消息摘要</digest>  "
				+ "<userName>通道商用户名</userName>" + "</header>" + "<body> " + " <responseCode>响应码</responseCode>  "
				+ "<responseMessage>响应消息</responseMessage>" + "<tickets> " + " <ticket> "
				+ "  <ticketId>票号</ticketId>  " + " <status>出票状态码</status>" + "<time>出票时间</time>  "
				+ "<TCid>中心票号</TCid>" + "</ticket>  " + "<ticket> " + " <ticketId>票号</ticketId>  "
				+ "<status>出票状态码</status>" + " <time>出票时间</time>  " + " <TCid>中心票号</TCid>" + " </ticket> "
				+ "</tickets>" + "</body>" + "</message>";
		CheckResponseMsg msg = new CheckResponseMsg().fromXML(string);
		System.out.println(msg.toXml());
	}


	@Override
	public String getCode() {
		return body.getResponseCode();
	}


	@Override
	public String getMessage() {
		return body.getResponseMessage();
	}


	@Override
	public List<? extends ICheckTicket> getTicket() {
		return body.getTickets();
	}


	@Override
	public boolean isSuccess() {
		return GaoDeDealer.SUCCESS.equals(getCode());
	}


	@Override
	public boolean isExist() {
		return true;
	}

}
