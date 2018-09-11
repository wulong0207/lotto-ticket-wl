package com.hhly.ticket.service.ticket.dealer.hongzhan.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 排列3
 * @author jiangwei
 * @date 2018年4月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Pl3HZConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "100";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		if(bo.getLotteryChildCode() == 10401){
			if(bo.getContentType() == 1){
				return "0";
			}else if(bo.getContentType() == 2){
				return "1";
			}
		}else if(bo.getLotteryChildCode() == 10402){
			if(bo.getContentType() == 1){
				return "3";
			}else if(bo.getContentType() == 2){
				return "5";
			}
		}else if(bo.getLotteryChildCode() == 10403){
			if(bo.getContentType() == 1){
				return "3";
			}else if(bo.getContentType() == 2){
				return "4";
			}
		}
		throw new ServiceRuntimeException("玩法错误");
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
