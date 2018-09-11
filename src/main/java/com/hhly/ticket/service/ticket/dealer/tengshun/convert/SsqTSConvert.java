package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import com.hhly.ticket.service.entity.TicketBO;

public class SsqTSConvert extends AbstractConvert {

	@Override
	protected String getContent(TicketBO bo) {
		String type = getType(bo.getContentType());
		String play = play(bo);
		StringBuilder sb = new StringBuilder();
		for (char c : bo.getTicketContent().toCharArray()) {
			switch (c) {
			case '+':
				sb.append("|");
				break;
			case '#':
				sb.append("$");
				break;
			case ';':
				sb.append(contentEnd(play, type));
				sb.append(";");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		sb.append(contentEnd(play, type));
		return sb.toString();
	}
}
