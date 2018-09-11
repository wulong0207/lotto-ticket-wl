package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 11选5
 * @author jiangwei
 * @date 2018年5月19日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class A11x5TSConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

	@Override
	protected String getContent(TicketBO bo) {
		String type = getType(bo.getContentType());
		String play = play(bo.getLotteryChildCode());
		StringBuilder sb = new StringBuilder();
		for (char c : bo.getTicketContent().toCharArray()) {
			switch (c) {
			case '|':
				if("13".equals(play)){//乐选3
					sb.append(",");
				}else{
					sb.append(c);
				}
				break;
			case ';':
				sb.append(contentEnd(play, type));
				sb.append(";");
				break;
			case '#':
				sb.append("$");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		sb.append(contentEnd(play, type));
		return sb.toString();
	}
    
	public String play(int childCode){
		childCode = childCode % 100;
		switch (childCode) {
		case 2:
			return "2";// 十一选五任选二
		case 3:
			return "3";// 十一选五任选三
		case 4:
			return "4";// 十一选五任选四
		case 5:
			return "5";// 十一选五任选五
		case 6:
			return "6";// 十一选五任选六
		case 7:
			return "7";// 十一选五任选七
		case 8:
			return "8";// 十一选五任选八
		case 9:
			return "1";// 十一选五前一
		case 10:
			return "11";// 十一选五前二组选
		case 11:
			return "9";// 十一选五前二直选
		case 12:
			return "12";// 十一选五前三组选
		case 13:
			return "10";// 十一选五前三直选
		case 14:
			throw new ServiceRuntimeException("不存在子玩法");// 乐二
		case 15:
			return "13";// 乐三
		case 16:
			return "14";// 乐四
		case 17:
			return "15";// 乐五
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	
	}
}
