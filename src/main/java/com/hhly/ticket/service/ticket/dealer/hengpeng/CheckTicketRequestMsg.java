package com.hhly.ticket.service.ticket.dealer.hengpeng;

import java.util.ArrayList;
import java.util.List;

import com.hhly.ticket.service.ticket.dealer.hengpeng.request.CheckTicket;
import com.hhly.ticket.service.ticket.dealer.hengpeng.request.CheckTicketBody;
import com.hhly.ticket.service.ticket.dealer.hengpeng.request.CheckTicketQuery;
import com.hhly.ticket.util.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class CheckTicketRequestMsg extends AbstractMsg{
	
    private CheckTicketBody body;
    
    public CheckTicketRequestMsg(List<CheckTicket> ticket){
    	this.body = new CheckTicketBody();
    	CheckTicketQuery ticketQuery = new CheckTicketQuery();
    	this.body.setTicketQuery(ticketQuery); 
    	ticketQuery.setTicket(ticket);
    }

	public CheckTicketBody getBody() {
		return body;
	}

	public void setBody(CheckTicketBody body) {
		this.body = body;
	}
	
    public static void main(String[] args) {
    	List<CheckTicket> tickets = new ArrayList<>();
    	for (int i = 0; i < 3; i++) {
    		tickets.add(new CheckTicket(i+""));
		}
    	CheckTicketRequestMsg msg = new CheckTicketRequestMsg(tickets);
		msg.setId("1");
		Header header = new Header();
		header.setMessengerID("12346");
		header.setTransactionType("101");
		header.setDigest("1234");
		header.setTimestamp(DateUtil.getNow(DateUtil.DATE_FORMAT_NUM));
		msg.setHeader(header);
		System.out.println(msg.toXml());
	}
    
}
