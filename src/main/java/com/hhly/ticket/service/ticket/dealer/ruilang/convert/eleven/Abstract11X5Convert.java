package com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.AbstractConvert;

/**
 * @desc 11选5格式转换
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Abstract11X5Convert extends AbstractConvert {

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-");
		switch (bo.getContentType()) {
		case 1:// 单
			sb.append(getSimple(childClass));
			// 前3，2
			switch (childClass) {
			case "09":
			case "10":
			case "13":
			case "14":
				content = content.replaceAll(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR, SymbolConstants.COMMA);
				break;
			default:
				break;
			}
			content = content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND);
			break;
		case 2:// 复
			sb.append("02-");
			// 前3，2
			switch (childClass) {
			case "09":
			case "10":
				content = content.replaceAll(SymbolConstants.COMMA, SymbolConstants.SPACE);
				content = content.replaceAll(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR, SymbolConstants.COMMA);
				break;
			default:
				break;
			}
			break;
		case 3:// 胆
			sb.append("03-");
			content = content.replaceAll(SymbolConstants.NUMBER_SIGN, SymbolConstants.AT);
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
	/**
	 * 获取单式类型
	 * @author jiangwei
	 * 
	 * @Version 1.0
	 * @CreatDate 2017年10月11日 下午4:36:38
	 * @param childClass
	 * @return
	 */
	private String getSimple(String childClass) {
		switch (childClass) {
		case "13":
			return "12-";
		case "14":
			return "13-";
		case "15":
			return "14-";
		case "16":
			return "15-";
		default:
			return "01-";
		}
	}
}
