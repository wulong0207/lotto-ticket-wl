package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 七乐彩
 * @author jiangwei
 * @date 2017年11月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class QlcJXConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "7";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		if(bo.getContentType() == 1){
			return "1000";
		}else if(bo.getContentType() == 2){
			return "1001";
		}else{
			return "1002";	
		}
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent().replaceAll(",", "\\|");
		if(bo.getContentType() == 3){
			content = content.replace("#", "|-");
		}else if(bo.getContentType() == 1){
			content = content.replace(";", "|;");
		}
		if(bo.getContentType() == 1){
			content += "|";
		}else{
			content += "|-";
		}
		return content;
	}

}
