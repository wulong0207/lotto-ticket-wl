package com.hhly.ticket.service.ticket.dealer.jimi.convert;

import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 七乐彩
 * @author jiangwei
 * @date 2018年4月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class QlcJMConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(2);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "62";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		return String.valueOf(bo.getContentType());
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		StringBuilder sb = new StringBuilder(playType);
		sb.append("^");
		for (char c : bo.getTicketContent().toCharArray()) {
			switch (c) {
			case ',':
				sb.append("/");
				break;
			case ';':
				sb.append(",");
				break;
			case '#':
				sb.append("//");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

}
