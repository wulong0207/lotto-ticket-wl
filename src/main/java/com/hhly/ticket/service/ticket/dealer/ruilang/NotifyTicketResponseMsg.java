package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.response.CheckOrderTicket;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.NotifyTicketBody;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("msg")
public class NotifyTicketResponseMsg  extends AbstractMsg{
	
	private static XStream  XS;
	
	static{
		XS= XmlUtil.createXStream(NotifyTicketResponseMsg.class);
		XS.registerConverter(new CheckOrderTicket());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	private NotifyTicketBody body;

	public NotifyTicketBody getBody() {
		return body;
	}

	public void setBody(NotifyTicketBody body) {
		this.body = body;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		String string = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><msg v=\"1000\" id=\"1d6604524ba2412a9feebee7b29b3847\"><ctrl><agentID>800213</agentID><cmd>1004</cmd><timestamp>20170519154619</timestamp><md>fc35ca0d70a560da465e62553ca12bf9</md></ctrl><body><order merchantid=\"800213\" orderno=\"PC1705191512100100001\" issue=\"2017058\" lotoid=\"001\" status=\"1\"><ticket seq=\"1\" status=\"Y\" errmsg=\"出票成功\" ticketid=\"53302017051900000165\" groundid=\"\"/></order></body></msg>";
		NotifyTicketResponseMsg msg = NotifyTicketResponseMsg.class.newInstance().fromXML(string);
		System.out.println(JsonUtil.object2Json(msg));
	}
	
}
