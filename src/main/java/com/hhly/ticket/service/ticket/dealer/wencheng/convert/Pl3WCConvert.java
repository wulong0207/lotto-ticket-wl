package com.hhly.ticket.service.ticket.dealer.wencheng.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

public class Pl3WCConvert extends AbstractConvert {

	@Override
	public String getPlayType(TicketBO bo) {
		if (bo.getLotteryChildCode() == 10401) {
			return "1";
		} else if (bo.getLotteryChildCode() == 10402) {
			return "2";
		} else {
			return "3";
		}

	}

	@Override
	public String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	public String getLotteryCode(TicketBO bo) {
		return "22";
	}

	@Override
	public String getContent(TicketBO bo) {
		String result = bo.getTicketContent();
		if (bo.getLotteryChildCode() == 10401) {

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
			result = sb.toString();
		}
		return result;
	}

	@Override
	public String getContentType(TicketBO bo) {
		String result = "";
		if (bo.getLotteryChildCode() == 10401) {
			if (bo.getContentType() == 1) {
				result = "1";
			} else if (bo.getContentType() == 2) {
				result = "2";
			} else if (bo.getContentType() == 6) {
				result = "3";
			}
		} else if (bo.getLotteryChildCode() == 10402) {
			if (bo.getContentType() == 1) {
				result = "4";
			} else if (bo.getContentType() == 2) {
				result = "5";
			} else if (bo.getContentType() == 6) {
				result = "6";
			}
		} else {
			if (bo.getContentType() == 1) {
				result = "7";
			} else if (bo.getContentType() == 2) {
				result = "8";
			} else if (bo.getContentType() == 6) {
				result = "9";
			}
		}
		if("".equals(result)){
			throw new ServiceRuntimeException("玩法错误");
		}
		return result;
	}
}
