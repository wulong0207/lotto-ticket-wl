package com.hhly.ticket.service.ticket.dealer.jimi.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 排列5
 * @author jiangwei
 * @date 2018年4月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Pl5JMConvert extends AbstractConvert {
	
	
	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "53";
	}
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}
	
	@Override
	protected String getPlayType(TicketBO bo) {
		switch (bo.getContentType()) {
		case 1:
			return "1";
		case 2:
			return "2";
		default:
			throw new ServiceRuntimeException("不存在的ContentType,票bo为："+bo);
		}
	}
	
	/**
     *  本系统    ->过关方式^投注号码
     *  单式示例：6|6|2|3|2;7|6|7|6|7;1|3|6|3|1 -> 1^6/6/2/3/2,7/6/7/6/7,1/3/6/3/1
     *  复式示例：1|8,9|4,5|6|2,7 -> 2^1//8/9//4/5//6//2/7
     */
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		switch (playType) {
		case "1":
			content = content.replaceAll("\\|", "/").replaceAll(";", ",");
			break;
		case "2":
			content = content.replaceAll("\\|", "//").replaceAll(",", "/");
			break;
		default:
			throw new ServiceRuntimeException("不支持的playType,票bo为："+bo);
		}
		return playType + "^" + content;
	}
}
