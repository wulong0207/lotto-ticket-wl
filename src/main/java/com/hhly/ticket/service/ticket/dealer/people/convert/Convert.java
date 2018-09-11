package com.hhly.ticket.service.ticket.dealer.people.convert;

import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;

public  class Convert implements IConvert {

	@Override
	public boolean handle(TicketBO bo) {
		bo.setChannelLotteryCode("1");
		bo.setChannelLotteryIssue("2");
		bo.setChannelPlayType("3");
		return true;
	}
   
}
