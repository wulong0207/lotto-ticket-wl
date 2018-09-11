package com.hhly.ticket.service.ticket.dealer.hongzhan.convert;

import com.hhly.ticket.service.entity.TicketBO;
/**
 * 
 * @desc 排列5
 * @author jiangwei
 * @date 2018年4月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Pl5HZConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "102";
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		StringBuilder sb = new StringBuilder();
		for(char c :bo.getTicketContent().toCharArray()){
			switch (c) {
			case ',':
				break;
			case '|':
				sb.append("*");
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
