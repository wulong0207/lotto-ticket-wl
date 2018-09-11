package com.hhly.ticket.service.ticket.dealer.ruilang.convert;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 七乐彩送票格式转换
 * @author jiangwei
 * @date 2017年7月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class QlcConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "003";
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
		// 1：单式；2：复式；3：胆拖；
		switch (bo.getContentType()) {
		case 1:
			sb.append("01-");
			content = content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND);
			break;
		case 2:
			sb.append("02-");
			break;
		case 3:
			content = content.replaceAll(SymbolConstants.NUMBER_SIGN, SymbolConstants.AT);
			sb.append("03-");
			break;
		default:
			throw new ServiceRuntimeException("不存在内容类型");
		}
		sb.append(content);
		sb.append("-");
		sb.append(bo.getMultipleNum());
		sb.append("-");
		sb.append((int) bo.getTicketMoney());
		return sb.toString();
	}

}
