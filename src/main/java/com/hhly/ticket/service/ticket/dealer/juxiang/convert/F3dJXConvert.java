package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 福彩3d
 * @author jiangwei
 * @date 2017年11月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class F3dJXConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "6";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		int type = bo.getContentType();
		switch (bo.getLotteryChildCode()) {
		case 10501:
			if(type == 2){
				return "2020";
			}else if(type == 6){
				return "2051";
			}
			return "2000";
		case 10502:
			if(type == 2){
				return "2030";
			}else if(type == 6){
				return "2053";
			}
			return "2010";
		case 10503:
			if(type == 2){
				return "2031";
			}else if(type == 6){
				return "2054";
			}
			return "2011";
		default:
			break;
		}
		throw new ServiceRuntimeException("不能存在子玩法");
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		if(bo.getContentType() == 6){
			//和值
			return bo.getTicketContent() + "|";
		}
		char [] codes = bo.getTicketContent().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : codes) {
			switch (c) {
			case ',':
				sb.append("|");
				break;
			case '|':
				if(bo.getContentType() == 1){
					sb.append("|");
				}else{
					sb.append("|-");	
				}
				break;
			case ';':
				sb.append("|;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		if(bo.getContentType() == 1){
			sb.append("|");
		}else{
			sb.append("|-");
		}
		return sb.toString();
	}

}
