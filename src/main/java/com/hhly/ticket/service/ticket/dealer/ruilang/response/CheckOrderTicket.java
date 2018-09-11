package com.hhly.ticket.service.ticket.dealer.ruilang.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@XStreamAlias("ticket")
public class CheckOrderTicket implements Converter{
	private String seq;
	
	private String status;
	
	private String errmsg;
	
	private String ticketid;
	
	private String groundid;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}

	public String getGroundid() {
		return groundid;
	}

	public void setGroundid(String groundid) {
		this.groundid = groundid;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return type.equals(this.getClass());
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		CheckOrderTicket ticket = new CheckOrderTicket(); 
		ticket.setSeq(reader.getAttribute("seq"));
		ticket.setErrmsg(reader.getAttribute("errmsg"));
		ticket.setStatus(reader.getAttribute("status"));
		ticket.setGroundid(reader.getAttribute("groundid"));
		ticket.setTicketid(reader.getAttribute("ticketid"));
		ticket.setValue(reader.getValue());
        return ticket; 
	}

	@Override
	public String toString() {
		return "CheckOrderTicket [seq=" + seq + ", status=" + status + ", errmsg=" + errmsg + ", ticketid=" + ticketid
				+ ", groundid=" + groundid + ", value=" + value + "]";
	}
	
}
