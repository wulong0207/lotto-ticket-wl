package com.hhly.ticket.service.ticket.dealer.ruilang.convert.k3;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 江苏快3格式转换
 * @author jiangwei
 * @date 2017年5月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JSK3Convert extends AbstractQuickThreeConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "027";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 23301://和值
			return "01";
		case 23307://三同号通选
			return "03";
		case 23306://三同号单选
			return "02";
		case 23305://三不同号
			return "05";
		case 23308://三连号通选
			return "04";
		case 23303://二同号复选
			return "07";
		case 23302://二同号单选
			return "06";
		case 23304://二不同号
			return "08";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
		
	}
	public static void main(String[] args) {
		JSK3Convert convert = new JSK3Convert();
		TicketBO bo = new TicketBO();
		bo.setContentType(1);
		bo.setMultipleNum(1);
		bo.setTicketMoney(6);
		bo.setTicketContent("11*");
		bo.setLotteryChildCode(23306);
		convert.handle(bo);
		System.out.println(bo.getChannelTicketContent());
	}

}
