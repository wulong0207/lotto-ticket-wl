package com.hhly.ticket.service.ticket.dealer.ruilang.convert;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 老足彩格式装换
 * @author jiangwei
 * @date 2017年7月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class LzcConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryCode()) {
		case 302://六场半全场
			return "115";
		case 303://四场进球彩
			return "116";
		case 304://胜负彩
			return "117";
		case 305://九场胜负彩
			return "118";
		default:
			throw  new ServiceRuntimeException("老足彩彩种编码错误");
		}
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		return "00";
	}

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-");
		// 1：单式；2：复式；
		switch (bo.getContentType()) {
		case 1:
			sb.append("01-");
			content = content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND);
			break;
		case 2:
			sb.append("02-");
			content = content.replaceAll(SymbolConstants.COMMA, SymbolConstants.SPACE);
			break;
		default:
			throw new ServiceRuntimeException("不存在内容类型");
		}
		content = content.replaceAll(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR, SymbolConstants.COMMA);
		sb.append(content);
		sb.append("-");
		sb.append(bo.getMultipleNum());
		sb.append("-");
		sb.append((int) bo.getTicketMoney());
		return sb.toString();
	}

}
