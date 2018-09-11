package com.hhly.ticket.service.ticket.dealer.gaode.request;

import com.hhly.ticket.service.ticket.dealer.gaode.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 检票
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("body")
public class CheckBody extends AbstractXml {
	
    private CheckTicket tickets;

	public CheckTicket getTickets() {
		return tickets;
	}

	public void setTickets(CheckTicket tickets) {
		this.tickets = tickets;
	}

	
}
