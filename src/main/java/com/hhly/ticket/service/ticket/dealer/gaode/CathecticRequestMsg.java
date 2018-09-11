package com.hhly.ticket.service.ticket.dealer.gaode;

import java.util.ArrayList;
import java.util.List;

import com.hhly.ticket.service.ticket.dealer.gaode.request.BjdcCathecticBody;
import com.hhly.ticket.service.ticket.dealer.gaode.request.CathecticBody;
import com.hhly.ticket.service.ticket.dealer.gaode.request.CathecticTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 北京单场投注请求
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class CathecticRequestMsg extends AbstractMsg {
	
	private CathecticBody body;

	public CathecticBody getBody() {
		return body;
	}

	public void setBody(CathecticBody body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		CathecticRequestMsg msg = new CathecticRequestMsg();
		Header header = new Header();
    	header.setDigest("23");
    	header.setTimestamp("1");
    	header.setTransactionType("132");
    	header.setUserName("yicai");
    	msg.setHeader(header);
    	BjdcCathecticBody body = new BjdcCathecticBody();
    	msg.setBody(body);
    	body.setIssue("1234");
    	body.setLotteryId("100");
    	body.setRule("2 串 1");
    	List<CathecticTicket> tickets = new ArrayList<>();
    	body.setTickets(tickets);
    	for (int i = 0; i < 2; i++) {
    		CathecticTicket ticket = new CathecticTicket();
    		ticket.setBetContents("1");
    		ticket.setChips("2");
    		ticket.setMoney("3");
    		ticket.setMultiple("4");
    		ticket.setPassMode("5");
    		ticket.setTicketId(i+"");
    		tickets.add(ticket);
		}
    	System.out.println(msg.toXml());
	}
	  
}
