package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 老足彩格式转换
 * @author jiangwei
 * @date 2017年10月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class LzcYSConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryCode()) {
		case 302://六场半全场
			return "304";
		case 303://四场进球彩
			return "303";
		case 304://十四场胜负彩
			return "301";
		case 305://九场胜负彩
			return "302";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		if(bo.getContentType() == 1){
			return "1";
		}else if(bo.getContentType() == 2){
			return "2";
		}
		throw new ServiceRuntimeException("老足彩只能单式或者复试投注");
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		char[] contents = bo.getTicketContent().toCharArray();
		StringBuilder sb =new StringBuilder(playType);
		sb.append("^");
		for (char c : contents) {
			switch (c) {
			case '|':
				if(bo.getContentType() == 1){
					sb.append("/");
				}else{
					sb.append("//");
				}
				break;
			case '_':
				sb.append("*");
				break;
			case ';':
				sb.append(",");
				break;
			case ',':
				sb.append("/");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

}
