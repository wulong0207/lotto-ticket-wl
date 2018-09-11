package com.hhly.ticket.service.ticket.dealer.hongzhan.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 大乐透
 * @author jiangwei
 * @date 2018年4月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DltHZConvert extends AbstractConvert {


	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "106";
	}
	

	@Override
	protected String getContent(String playType, TicketBO bo) {
		StringBuilder sb = new StringBuilder();
		for(char c :bo.getTicketContent().toCharArray()){
			switch (c) {
			case ',':
				break;
			case '+':
				sb.append("|");
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

	@Override
	protected String getPlayType(TicketBO bo) {
		if(bo.getContentType() == 1){
			return "0";
		}else if(bo.getContentType() == 2){
			return "1";
		}
		throw new ServiceRuntimeException("鸿展大乐透不支持胆拖");
	}


	@Override
	public String getChannelContentType(TicketBO bo) {
		if(bo.getLottoAdd() == 1){
			return "1";
		}
		return super.getChannelContentType(bo);
	}
	
	@Override
	protected int getChips(TicketBO bo) {
		if(bo.getLottoAdd() == 1){
			return ((int) bo.getTicketMoney()) / bo.getMultipleNum() / 3;
		}else{
			return super.getChips(bo);
		}
	}
	
}
