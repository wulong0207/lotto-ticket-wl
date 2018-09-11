package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 七星彩格式转换
 * @author jiangwei
 * @date 2017年11月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class QxcJXConvert extends AbstractConvert {
	
	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "2002";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		if (bo.getContentType() == 1) {
			return "1";
		}
		return "2";
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		if (bo.getContentType() == 1) {
			content = content.replaceAll(";", "|;");
		}else{
			content = content.replaceAll(",", "");
		}
		return content + "|";
	}

}
