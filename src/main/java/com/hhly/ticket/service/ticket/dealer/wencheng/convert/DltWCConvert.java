package com.hhly.ticket.service.ticket.dealer.wencheng.convert;

import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 大乐透
 * @author jiangwei
 * @date 2018年8月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DltWCConvert extends AbstractConvert {

	@Override
	public String getPlayType(TicketBO bo) {
		if (bo.getLottoAdd() == 0) {
			return "1";
		}
		return "2";
	}

	@Override
	public String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	public String getLotteryCode(TicketBO bo) {
		return "21";
	}

	@Override
	public String getContent(TicketBO bo) {
		StringBuilder sb = new StringBuilder();
		for (char c : bo.getTicketContent().toCharArray()) {
			switch (c) {
			case '+':
				sb.append("-");
				break;
			case '#':
				sb.append("|");
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
		return bo.getContentType()+"";
	}
}
