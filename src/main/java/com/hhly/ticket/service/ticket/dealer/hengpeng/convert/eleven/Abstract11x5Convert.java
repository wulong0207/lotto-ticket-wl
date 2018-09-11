package com.hhly.ticket.service.ticket.dealer.hengpeng.convert.eleven;

import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.hengpeng.convert.AbstractConvert;

public abstract class Abstract11x5Convert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}


	@Override
	protected String getContent(String playType, TicketBO bo) {
		return  bo.getTicketContent();
	}

}
