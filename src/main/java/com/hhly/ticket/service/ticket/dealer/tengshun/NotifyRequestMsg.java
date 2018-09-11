package com.hhly.ticket.service.ticket.dealer.tengshun;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.tengshun.response.NotifyTicket;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("notify")
public class NotifyRequestMsg extends AbstractMsg implements ICheckResponse{
	
	
	private static XStream  XS = XmlUtil.createXStream(NotifyRequestMsg.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	@XStreamAsAttribute
	private String agent;
	@XStreamAsAttribute
	private String type;
	
	@XStreamImplicit
	private List<NotifyTicket> tickets;
	
	
	@Override
	public String getCode() {
		return type;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return tickets;
	}

	@Override
	public boolean isSuccess() {
		return "30000".equals(type);
	}

	@Override
	public boolean isExist() {
		return false;
	}
	

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<NotifyTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<NotifyTicket> tickets) {
		this.tickets = tickets;
	}
	
}
