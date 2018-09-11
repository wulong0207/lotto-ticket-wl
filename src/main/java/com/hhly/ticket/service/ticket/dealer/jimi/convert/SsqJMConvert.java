package com.hhly.ticket.service.ticket.dealer.jimi.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * 
 * @desc 双色球
 * @author jiangwei
 * @date 2018年3月30日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SsqJMConvert extends AbstractConvert {
	
	
	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "61";
	}
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(2);
	}
	
	@Override
	protected String getPlayType(TicketBO bo) {
		return String.valueOf(bo.getContentType());
	}
	
	@Override
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		// 1：单式；2：复式；3：胆拖；
		switch (playType) {
		case "1":
			content = content.replaceAll(",", "/").replaceAll("\\+", "//").replaceAll(";", ",");
			break;
		case "2":
			content = content.replaceAll(",", "/").replaceAll("\\+", "//");
			break;
		case "3":
			// 耀森的格式，后区没胆码时则后区没有"//"
			content = content.replaceAll(",", "/").replaceAll("#", "//").replaceAll("\\+", ",");
			break;
		default:
			throw new ServiceRuntimeException("不存在内容类型,票bo为："+bo);
		}
		return bo.getContentType() + "^" + content ;
	}
}
