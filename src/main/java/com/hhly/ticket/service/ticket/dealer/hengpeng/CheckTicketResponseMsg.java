package com.hhly.ticket.service.ticket.dealer.hengpeng;

import com.hhly.ticket.service.ticket.dealer.hengpeng.response.CheckBody;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class CheckTicketResponseMsg extends AbstractMsg {

	private static XStream XS = XmlUtil.createXStream(CheckTicketResponseMsg.class);

	private CheckBody body;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	public CheckBody getBody() {
		return body;
	}

	public void setBody(CheckBody body) {
		this.body = body;
	}

	public static void main(String[] args) {
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				+ "<message version=\"2.0\" id=\"3899122009101600000231\">" + "<header>"
				+ "<messengerID>389912</messengerID>" + "<timestamp>20091016092435</timestamp>"
				+ "<transactionType>501</transactionType>" + "<digest>879cba6a680b38bca06922fb72fd76d4</digest>"
				+ "</header><body><response code=\"0000\" message=\"成功,系统处理正常\" >" + "<ticketQueryResult>"
				+ "<ticket id=\"3899122009101600000031\" dealTime=\"20091016115401\" money=\"8.0\" playType=\"9\" amount=\"1\" status=\"0000\" message=\"成功，系统处理正常。\" ticketSerialNo=\"04E84-02E22-00FB3-1830A-00009\">"
				+ "<issue gameName=\"D11\" number=\"09101616\"/>"
				+ "<userProfile cardType=\"1\" cardNumber=\"430923197901011234\" mail=\"\" mobile=\"13813813813\" realName=\"麦兜\" userName=\"maidou\" bonusPhone=\"13867419164\"/>"
				+ "<anteCode>01,09|06,07</anteCode>" + "</ticket>"
				+ "<ticket id=\"3899122009101600000038\" dealTime=\"20091016115401\" money=\"30.0\" playType=\"1\" amount=\"3\" status=\"0000\" message=\"成功，系统处理正常。\" ticketSerialNo=\"04E84-02E22-00FB3-1830A-0000A\">"
				+ "<issue gameName=\"D11\" number=\"09101616\"/>"
				+ "<userProfile cardType=\"1\" cardNumber=\"430923197901011234\" mobile=\"13813813813\" realName=\"麦兜\" mail=\"\" userName=\"maidou\" bonusPhone=\"13867419164\"/>"
				+ "<anteCode>06</anteCode>  <anteCode>09</anteCode>  <anteCode>05</anteCode><anteCode>08</anteCode> <anteCode>01</anteCode>"
				+ "</ticket> </ticketQueryResult>" + "</response> </body></message>";
		System.out.println(str);
		CheckTicketResponseMsg msg = new CheckTicketResponseMsg().fromXML(str);
		System.out.println(JsonUtil.object2Json(msg));
	}

}
