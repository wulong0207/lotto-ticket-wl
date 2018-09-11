package com.hhly.ticket.service.ticket.dealer.hengpeng.convert;

import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 双色球格式转换
 * @author jiangwei
 * @date 2017年8月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
/**
 * @desc 双色球格式装换
 * @author jiangwei
 * @date 2017年8月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SsqHPConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "ssq";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		if(bo.getContentType() == 1){
			return "101";
		}else if(bo.getContentType() == 2){
			return "102";
		}else{
			//胆拖
			return "103";
		}
		
	}

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String content = bo.getTicketContent();
		if(bo.getContentType() == 3){
			content= content.replaceAll("\\#", "\\$");
		}
		return content.replaceAll("\\+", "#");
	}
}
