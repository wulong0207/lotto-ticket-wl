package com.hhly.ticket.service.ticket.dealer.gaode.request;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.gaode.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 投注body
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("body")
public class CathecticBody extends AbstractXml {
	
	private  String lotteryId;
	
	private String  rule;
	
	private List<CathecticTicket> tickets;

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public List<CathecticTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<CathecticTicket> tickets) {
		this.tickets = tickets;
	}
	
	
}
