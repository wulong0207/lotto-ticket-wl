package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 聚享大乐透
 * @author jiangwei
 * @date 2017年11月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DltJXConvert extends AbstractConvert {


	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "2001";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		String playType = "1";
		if (bo.getContentType() == 2) {
			String[] all = bo.getTicketContent().split("\\+");
			String[] before = all[0].split(",");
			String[] after = all[1].split(",");
			if (before.length > 5 && after.length > 2) {
				playType = "4";
			} else if (before.length > 5) {
				playType = "2";
			} else {
				playType = "3";
			}
		} else if (bo.getContentType() == 3) {
			String[] all = bo.getTicketContent().split("\\+");
			int before = all[0].indexOf("#");
			int after = all[1].indexOf("#");
			if (before != -1 && after != -1) {
				playType = "7";
			} else if (before != -1) {
				playType = "5";
			} else {
				playType = "6";
			}
		}
		if (bo.getLottoAdd() == 1) {
			playType = "1" + playType;
		}
		return playType;
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		char [] codes = bo.getTicketContent().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : codes) {
			switch (c) {
			case ',':
				sb.append("|");
				break;
			case '+':
				sb.append("|-");
				break;
			case ';':
				sb.append("|-;");
				break;
			case '#':
				sb.append("|*");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		sb.append("|-");
		return sb.toString();
	}

}
