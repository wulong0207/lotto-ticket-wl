package com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport;

import com.hhly.ticket.base.common.LotteryEnum;
import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.AbstractConvert;
/**
 * @desc 冠亚军
 * @author jiangwei
 * @date 2018年3月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class GuanYaConvert extends AbstractConvert {

	
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "201803062099";
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		if(bo.getLotteryCode() == LotteryEnum.Lottery.CHP.getName()){
			return "401";
		}else if(bo.getLotteryCode() == LotteryEnum.Lottery.FNL.getName()){
			return "402";	
		}
		throw new ServiceRuntimeException("彩种编号异常");
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		return "01";
	}

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String[] str = bo.getTicketContent().split(SymbolConstants.AT);
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-01-");
		//彩期固定
		sb.append("201803062099:[");
		sb.append(str[0]);
		sb.append("]-");
		sb.append(bo.getMultipleNum());
		sb.append("-");
		sb.append((int) bo.getTicketMoney());
		return sb.toString();
	}
}
