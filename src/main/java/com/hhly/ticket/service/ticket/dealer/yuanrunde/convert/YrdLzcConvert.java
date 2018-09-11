package com.hhly.ticket.service.ticket.dealer.yuanrunde.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * 老足彩转换渠道参数
 * @author wul687
 */
public class YrdLzcConvert extends AbstractConvert{

	@Override
	public String getPlayType(TicketBO bo) {
		String lotteryCode = getLotteryCode(bo);
		if(bo.getContentType()==1){
			return lotteryCode+"01";
		}else if(bo.getContentType()==2){
			return lotteryCode+"02";
		}else{
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}

	@Override
	public String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	public String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
			case 30401:
				return "5001";//足彩胜负彩
			case 30501:
			case 30502:
				return "5002";//足彩任九场
			default:
				throw new ServiceRuntimeException("不存在子玩法");
			}
	}

	@Override
	public String getContent(TicketBO bo) {
		String ticketContent = bo.getTicketContent();
		ticketContent = ticketContent.replaceAll(",", "").replaceAll("\\|", ",");
		switch (bo.getLotteryChildCode()) {
			case 30401:
				return ticketContent+"^";//足彩胜负彩
			case 30501:
			case 30502:
				return ticketContent.replaceAll("\\_", "~")+"^";//足彩任九场
			default:
				throw new ServiceRuntimeException("不存在子玩法");
		}
	}
}
