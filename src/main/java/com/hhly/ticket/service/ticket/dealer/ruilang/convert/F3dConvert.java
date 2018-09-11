package com.hhly.ticket.service.ticket.dealer.ruilang.convert;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 福彩3D
 * @author jiangwei
 * @date 2017年6月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class F3dConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "002";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 10501:
			return "01";
		case 10502:
			return "02";
		case 10503:
			return "03";
		default:
			throw new ServiceRuntimeException("不能存在子玩法");
		}
	}

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-");
		// 1：单式；2：复式；6：和值；
		switch (bo.getContentType()) {
		case 1:
			sb.append("01-");
			content = content.replaceAll(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR, SymbolConstants.COMMA);
			content = content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND);
			break;
		case 2:
			sb.append("02-");
			if("01".equals(childClass)){
				//直选复式
				content = content.replaceAll(SymbolConstants.COMMA, SymbolConstants.SPACE);
				content = content.replaceAll(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR, SymbolConstants.COMMA);
			}
			break;
		case 6:
			sb.append("04-");
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
