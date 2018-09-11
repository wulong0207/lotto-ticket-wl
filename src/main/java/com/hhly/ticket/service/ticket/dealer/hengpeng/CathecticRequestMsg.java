package com.hhly.ticket.service.ticket.dealer.hengpeng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hhly.ticket.service.ticket.dealer.hengpeng.request.CathecticBody;
import com.hhly.ticket.service.ticket.dealer.hengpeng.request.CathecticLotteryRequest;
import com.hhly.ticket.service.ticket.dealer.hengpeng.request.CathecticTicket;
import com.hhly.ticket.util.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 投注实体
 * @author jiangwei
 * @date 2017年8月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class CathecticRequestMsg extends AbstractMsg{
	
	private CathecticBody body;

	public CathecticRequestMsg(){
		
	}
	
	public CathecticRequestMsg(List<CathecticTicket> tickets){
		this.body = new CathecticBody();
		CathecticLotteryRequest request = new CathecticLotteryRequest();
		body.setLotteryRequest(request);
		request.setTicket(tickets);
	}
	public CathecticBody getBody() {
		return body;
	}

	public void setBody(CathecticBody body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		
		List<CathecticTicket> tickets = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Issue issue = new Issue("gameName", "number");
			UserProfile user = new UserProfile("jw","13322@163.com", "51105", "123456789", "dsfd", "12356");
			List<String> anteCode = Arrays.asList("124_46","dfd_456");
			CathecticTicket ticket = new CathecticTicket("1", "1", 4, 2, issue, user, anteCode);
			tickets.add(ticket);
		}
		CathecticRequestMsg msg = new CathecticRequestMsg(tickets);
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
