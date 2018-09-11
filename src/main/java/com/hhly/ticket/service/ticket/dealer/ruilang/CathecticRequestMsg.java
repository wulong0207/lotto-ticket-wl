package com.hhly.ticket.service.ticket.dealer.ruilang;

import java.util.ArrayList;
import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ruilang.request.Cathectic;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.CathecticOrder;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.CathecticTicket;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.CathecticUserInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 投注实体对象
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class CathecticRequestMsg extends AbstractMsg {
	/**
	 * 消息体
	 */
	private Cathectic body;
	
	public Cathectic getBody() {
		return body;
	}

	public void setBody(Cathectic body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		CathecticRequestMsg msg = new CathecticRequestMsg();
    	msg.setId("1000");
    	msg.setV("1.2");
    	Ctrl ctrl = new Ctrl();
    	ctrl.setAgentID("123");
    	ctrl.setCmd("2000");
    	ctrl.setMd("123897654fdafjdklajfdlk");
    	ctrl.setTimestamp(System.currentTimeMillis());
    	msg.setCtrl(ctrl);
    	Cathectic cathectic = new Cathectic();
    	msg.setBody(cathectic);
    	CathecticOrder order = new CathecticOrder();
    	cathectic.setOrder(order);
    	order.setAreaid("1");
    	order.setIssue("171205");
    	order.setLotoid("100");
    	order.setOrderno("PC124567987");
    	order.setUsername("jw");
    	CathecticUserInfo cathecticUserInfo = new CathecticUserInfo();
    	order.setUserinfo(cathecticUserInfo);
    	cathecticUserInfo.setCardno("2");
    	cathecticUserInfo.setCardtype("1");
    	cathecticUserInfo.setEmail("");
    	cathecticUserInfo.setMobile("18576469293");
    	cathecticUserInfo.setRealname("江伟");
    	List<CathecticTicket> tickets = new ArrayList<>();
    	for (int i = 0; i <10; i++) {
    		CathecticTicket ticket =new  CathecticTicket("0"+i, i+ "10-11");
    		tickets.add(ticket);
		}
    	order.setTicket(tickets);
        String string = msg.toXml();
        System.out.println(string);
        System.out.println(cathectic.toXml().replace("<?xml version=\"1.0\" ?>",""));
	}

	
}
