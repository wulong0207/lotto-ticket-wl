package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import com.hhly.ticket.service.entity.TicketBO;

public class Pl3TSConvert extends F3dTSConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

}
