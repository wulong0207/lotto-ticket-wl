package com.hhly.ticket.service.ticket.dealer.gaode;

import java.util.ArrayList;
import java.util.List;

import com.hhly.ticket.service.ticket.dealer.gaode.request.CheckBody;
import com.hhly.ticket.service.ticket.dealer.gaode.request.CheckTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 检票
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class CheckRequestMsg extends AbstractMsg {
	
    private CheckBody body;

	public CheckBody getBody() {
		return body;
	}

	public void setBody(CheckBody body) {
		this.body = body;
	}
    
    public static void main(String[] args) {
    	CheckRequestMsg msg = new CheckRequestMsg();
		Header header = new Header();
    	header.setDigest("23");
    	header.setTimestamp("1");
    	header.setTransactionType("132");
    	header.setUserName("yicai");
    	msg.setHeader(header);
    	CheckBody body = new CheckBody();
    	msg.setBody(body);
    	body.setTickets(new CheckTicket());
    	List<String> list = new ArrayList<>();
    	list.add("1");
    	list.add("2");
    	list.add("3");
    	body.getTickets().setTickets(list);
    	System.out.println(msg.toXml());
	}
}
