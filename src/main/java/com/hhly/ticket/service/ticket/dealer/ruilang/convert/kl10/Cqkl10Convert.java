package com.hhly.ticket.service.ticket.dealer.ruilang.convert.kl10;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 重庆快乐10分
 * @author jiangwei
 * @date 2017年9月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Cqkl10Convert extends AbstractKl10Convert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(2);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "021";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 22201://前一数投
			return "01";
		case 22202://前一红投
			return "02";
		case 22203://任选二
			return "05";
		case 22204://选二连组
			return "03";
		case 22205://选二连直
			return "04";
		case 22206://任选三
			return "06";
		case 22207://选三前组
			return "10";
		case 22208://选三前直
			return "09";
		case 22209://任选四
			return "07";
		case 22210://任选五
			return "08";
		default:
			throw new ServiceRuntimeException("不存在该子玩法");
		}
	}

}
