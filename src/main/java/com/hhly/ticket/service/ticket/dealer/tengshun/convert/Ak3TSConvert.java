package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

public class Ak3TSConvert extends AbstractConvert {

	@Override
	protected String getContent(TicketBO bo) {
		String type = getType(bo.getContentType());
		String play = play(bo);
		if ("1".equals(play)) {
			type = "4";
		}
		StringBuilder sb = new StringBuilder();
		String[] cn = bo.getTicketContent().split(";");
		for (String str : cn) {
			if (sb.length() > 0) {
				sb.append(contentEnd(play, type));
				sb.append(";");
			}
			switch (play) {
			case "2":
			case "5":
				sb.append("0,0,0");
				break;
			case "3":
				String code = str.substring(0, 1);
				sb.append(code);
				sb.append(",");
				sb.append(code);
				sb.append(",");
				sb.append(code);
				break;
			case "4":
			case "8":
				sb.append(str);
				break;
			case "6":
				sb.append(str.substring(0,1));
				break;
			case "7":
				sb.append(str.substring(0,1));
				sb.append("|");
				sb.append(str.substring(4));
				break;
			default:
				break;
			}
		}
		sb.append(contentEnd(play, type));
		return sb.toString();
	}

	@Override
	public String play(TicketBO bo) {
		switch (bo.getLotteryChildCode() % 100) {
		case 1:// 和值
			return "1";
		case 7:// 三同号通选
			return "2";
		case 6:// 三同号单选
			return "3";
		case 5:// 三不同号
			return "4";
		case 8:// 三连号通选
			return "5";
		case 3:// 二同号复选
			return "6";
		case 2:// 二同号单选
			return "7";
		case 4:// 二不同号
			return "8";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
	}

}
