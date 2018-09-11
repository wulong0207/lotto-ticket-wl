package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import com.hhly.ticket.service.entity.TicketBO;

public class F3dTSConvert extends AbstractConvert {

	@Override
	protected String getContent(TicketBO bo) {
		String type = getType(bo.getContentType());
		String play = play(bo);
		StringBuilder sb = new StringBuilder();
		for (char c : bo.getTicketContent().toCharArray()) {
			switch (c) {
			case '|':
				sb.append(",");
				break;
			case ',':
				if ("2".equals(type) && "1".equals(play)) {
					break;
				}
				sb.append(",");
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

	/**
	 * 子玩法
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月19日 上午11:14:27
	 * @param childCode
	 * @return
	 */
	@Override
	public String play(TicketBO bo) {
		int code = bo.getLotteryChildCode() % 100;
		if (1 == code) {
			return "1";
		} else if (2 == code) {
			return "2";
		} else {
			return "3";
		}
	}

}
