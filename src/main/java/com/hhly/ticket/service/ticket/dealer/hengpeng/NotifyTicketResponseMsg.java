package com.hhly.ticket.service.ticket.dealer.hengpeng;

import com.hhly.ticket.service.ticket.dealer.hengpeng.response.NofifyBody;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class NotifyTicketResponseMsg extends AbstractMsg {
	private static XStream XS = XmlUtil.createXStream(NotifyTicketResponseMsg.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	private NofifyBody body;

	public NofifyBody getBody() {
		return body;
	}

	public void setBody(NofifyBody body) {
		this.body = body;
	}

	public static void main(String[] args) {
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				+ "<message version=\"2.0\" id=\"3899122009101600000231\">" + "<header>"
				+ "<messengerID>389912</messengerID>" + "<timestamp>20091016092435</timestamp>"
				+ "<transactionType>501</transactionType>" + "<digest>879cba6a680b38bca06922fb72fd76d4</digest>"
				+ "</header><body> " 
				+ "<ticketNotify> "
				+ "<ticket id=\"3899122009101600000038\" dealTime=\"20091016115401\" status=\"0000\" message=\"成功，系统处理正常。\" ticketSerialNo=\"04E84-02E22-00FB3-1830A-0000A\"/>"  
				+ "<ticket id=\"3899122009101600000031\" dealTime=\"20091016115401\" status=\"0000\" message=\"成功，系统处理正常。\" ticketSerialNo=\"04E84-02E22-00FB3-1830A-00009\"/> "
				+ "</ticketNotify>"
				+ "</body></message>";
		System.out.println(str);
		NotifyTicketResponseMsg msg = new NotifyTicketResponseMsg().fromXML(str);
		System.out.println(JsonUtil.object2Json(msg));

	}

}
