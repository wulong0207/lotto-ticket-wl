package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 双色球格式转换
 * @author jiangwei
 * @date 2017年11月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SsqJXConvert extends AbstractConvert {


	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "4";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		if(bo.getContentType() == 1){
			return "3000";
		}else if(bo.getContentType() == 2){
			return "3001";
		}else{
			String[] drag = bo.getTicketContent().split("\\+")[1].split(",");
			if(drag.length == 1){
				return "3002";
			}
			return "3003";
		}
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		char [] codes = bo.getTicketContent().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : codes) {
			switch (c) {
			case ',':
				sb.append("|");
				break;
			case '+':
			case '#':
				sb.append("|-");
				break;
			case ';':
				sb.append("|-;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		sb.append("|-");
		return sb.toString();
	}

}
