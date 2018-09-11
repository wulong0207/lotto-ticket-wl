package com.hhly.ticket.service.ticket.dealer.ruilang.convert.kl10;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.RuiLangUtil;
/**
 * @desc 广东快乐10分
 * @author jiangwei
 * @date 2017年8月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Gdkl10Convert extends AbstractKl10Convert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return RuiLangUtil.issueToXX(bo.getLotteryIssue());
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "025";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 22101://前一数投
			return "01";
		case 22102://前一红投
			return "02";
		case 22103://任选二
			return "05";
		case 22104://选二连组
			return "03";
		case 22105://选二连直
			return "04";
		case 22106://任选三
			return "06";
		case 22107://选三前组
			return "10";
		case 22108://选三前直
			return "09";
		case 22109://任选四
			return "07";
		case 22110://任选五
			return "08";
		default:
			throw new ServiceRuntimeException("不存在该子玩法");
		}
	}

}
