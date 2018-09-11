package com.hhly.ticket.service.ticket.dealer.hongzhan.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * 
 * @desc 广东11选5
 * @author jiangwei
 * @date 2018年4月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Gd11x5HZConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "121";
	}
	
	@Override
	public String getChannelContentType(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 21002:
			return "2";// 广东十一选五任选二
		case 21003:
			return "3";// 广东十一选五任选三
		case 21004:
			return "4";// 广东十一选五任选四
		case 21005:
			return "5";// 广东十一选五任选五
		case 21006:
			return "6";// 广东十一选五任选六
		case 21007:
			return "7";// 广东十一选五任选七
		case 21008:
			return "8";// 广东十一选五任选八
		case 21009:
			return "1";// 广东十一选五前一
		case 21010:
			return "11";// 广东十一选五前二组选
		case 21011:
			return "9";// 广东十一选五前二直选
		case 21012:
			return "12";// 广东十一选五前三组选
		case 21013:
			return "10";// 广东十一选五前三直选	
		case 21014:
			return "13";// 乐二
		case 21015:
			return "14";// 乐三
		case 21016:
			return "15";// 乐四
		case 21017:
			return "16";// 乐五
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}
	

	@Override
	protected String getContent(String playType, TicketBO bo) {
		StringBuilder sb = new StringBuilder();
		for(char c :bo.getTicketContent().toCharArray()){
			switch (c) {
			case ',':
			case '|':
				break;
			case ';':
				sb.append("^");
				break;
			case '#':
				sb.append("*");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	
	
	

}
