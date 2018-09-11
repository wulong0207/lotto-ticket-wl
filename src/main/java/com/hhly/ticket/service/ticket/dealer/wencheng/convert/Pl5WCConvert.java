package com.hhly.ticket.service.ticket.dealer.wencheng.convert;

import com.hhly.ticket.service.entity.TicketBO;

public class Pl5WCConvert extends AbstractConvert {

	@Override
	public String getPlayType(TicketBO bo) {
		return "1";
	}

	@Override
	public String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	public String getLotteryCode(TicketBO bo) {
		return "23";
	}

	@Override
	public String getContent(TicketBO bo) {
		StringBuilder sb = new StringBuilder();
		for (char c : bo.getTicketContent().toCharArray()) {
			switch (c) {
			case '|':
				sb.append(",");
				break;
			case ',':
				sb.append(" ");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	@Override
	public String getContentType(TicketBO bo) {
		return bo.getContentType() + "";
	}

}
