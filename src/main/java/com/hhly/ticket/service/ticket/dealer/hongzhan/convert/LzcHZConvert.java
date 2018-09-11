package com.hhly.ticket.service.ticket.dealer.hongzhan.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * 
 * @desc 老足彩
 * @author jiangwei
 * @date 2018年4月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class LzcHZConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryCode()) {
		case 302://六场半全场
			return "110";
		case 303://四场进球彩
			return "111";
		case 304://十四场胜负彩
			return "108";
		case 305://九场胜负彩
			return "109";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		StringBuilder sb = new StringBuilder();
		for(char c :bo.getTicketContent().toCharArray()){
			switch (c) {
			case ',':
				break;
			case '|':
				if("1".equals(playType)){
					sb.append("*");
				}
				break;
			case '_':
				sb.append("#");
				break;
			case ';':
				sb.append("^");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

}
