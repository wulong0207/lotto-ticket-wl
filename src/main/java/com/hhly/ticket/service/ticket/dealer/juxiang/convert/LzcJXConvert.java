package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 聚享老足彩
 * @author jiangwei
 * @date 2018年5月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class LzcJXConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryCode()) {
		case 302://六场半全场
			return "2017";
		case 303://四场进球彩
			return "2018";
		case 304://十四场胜负彩
			return "2006";
		case 305://九场胜负彩
			return "2006";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存彩种");
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		switch (bo.getLotteryCode()) {
		case 302://六场半全场
			return "7001";
		case 303://四场进球彩
			return "8001";
		case 304://十四场胜负彩
			return "6001";
		case 305://九场胜负彩
			return "6002";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		StringBuilder sb = new StringBuilder();
		for (char c : bo.getTicketContent().toCharArray()) {
			switch (c) {
			case ',':
				break;
			case '_':
				sb.append("-");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		sb.append("|");
		return sb.toString();
	}

}
