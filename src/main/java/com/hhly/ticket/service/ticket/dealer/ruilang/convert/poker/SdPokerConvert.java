package com.hhly.ticket.service.ticket.dealer.ruilang.convert.poker;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.AbstractConvert;

/**
 * @desc 山东快乐扑克3
 * @author jiangwei
 * @date 2017年7月13日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SdPokerConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "103";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 22501:// 山东快乐扑克3任1
			return "01";
		case 22502:// 山东快乐扑克3任2
			return "02";
		case 22503:// 山东快乐扑克3任3
			return "03";
		case 22504:// 山东快乐扑克3任4
			return "04";
		case 22505:// 山东快乐扑克3任5
			return "05";
		case 22506:// 山东快乐扑克3任6
			return "06";
		case 22507:// 山东快乐扑克3同花
			if ("AT".equalsIgnoreCase(bo.getTicketContent())) {
				// 包选
				return "07";
			}
			return "08";
		case 22508:// 山东快乐扑克3顺子
			if ("X,Y,Z".equals(bo.getTicketContent())) {
				return "11";
			}
			return "12";
		case 22509:// 山东快乐扑克3对子
			if ("X,X".equals(bo.getTicketContent())) {
				return "15";
			}
			return "16";
		case 22510:// 山东快乐扑克3豹子
			if ("Y,Y,Y".equals(bo.getTicketContent())) {
				return "13";
			}
			return "14";
		case 22511:// 山东快乐扑克3同花顺
			if ("AS".equals(bo.getTicketContent())) {
				return "09";
			}
			return "10";
		default:
			throw new ServiceRuntimeException("山东快乐扑克不存在玩法");
		}
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
			if (bo.getLotteryChildCode() <= 22506) {// 任选
				content = content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND);
			} else {
				content = other(childClass, content);
			}
			break;
		case 2:
			sb.append("02-");
			break;
		case 3:
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
	 * 同花，同花顺，顺子，对子，豹子
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月13日 下午3:18:10
	 * @param childClass
	 * @param content
	 * @return
	 */
	private String other(String childClass, String content) {
		// 黑桃：1;
		// 红心：2;
		// 梅花：3;
		// 方块：4;
		StringBuilder sb = new StringBuilder();
		//出票商对应玩法的格式转换
		switch (childClass) {
		case "07":
		case "09":
		case "11":
		case "13":
		case "15":
			sb.append("O");
			break;
		case "08":
		case "10":
			String str = content.substring(0, 1);
			if (str.equals("1")) {
				sb.append("S");
			} else if (str.equals("2")) {
				sb.append("H");
			} else if (str.equals("3")) {
				sb.append("C");
			} else if (str.equals("4")) {
				sb.append("D");
			}
			break;
		case "12":
		case "14":
		case "16":
			sb.append(content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND));
			break;
		default:
			throw new ServiceRuntimeException("山东快乐扑克3格式装换错误");
		}
		return sb.toString();
	}

}
