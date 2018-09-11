package com.hhly.ticket.service.ticket.dealer.gaode.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 老足彩
 * @author jiangwei
 * @date 2017年10月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class LzcGDConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "400";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryCode()) {
		case 302://六场半全场
			return "3";
		case 303://四场进球彩
			return "4";
		case 304://十四场胜负彩
			return "1";
		case 305://九场胜负彩
			return "2";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
	}

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		bo.setChannelContentType(String.valueOf(bo.getContentType()));
		char[] chars = bo.getTicketContent().toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			switch (c) {
			case '|':
				sb.append("/");
				break;
			case '_':
			case ',':
				break;
			case ';':
				sb.append("|");
			    break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	
	

}
