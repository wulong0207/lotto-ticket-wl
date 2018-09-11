package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import com.hhly.ticket.service.entity.TicketBO;

public class DltTSConvert extends SsqTSConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

	@Override
	public String play(TicketBO bo) {
		return bo.getLottoAdd() == 0 ? "1" : "2";
	}

}
