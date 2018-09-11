package com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 江西11选5
 * @author jiangwei
 * @date 2017年11月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JX11X5Convert extends Abstract11X5Convert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "106";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 21302:
			return "02";// 十一选五任选二
		case 21303:
			return "03";// 十一选五任选三
		case 21304:
			return "04";// 十一选五任选四
		case 21305:
			return "05";// 十一选五任选五
		case 21306:
			return "06";// 十一选五任选六
		case 21307:
			return "07";// 十一选五任选七
		case 21308:
			return "08";// 十一选五任选八
		case 21309:
			return "01";// 十一选五前一
		case 21310:
			return "11";// 十一选五前二组选
		case 21311:
			return "09";// 十一选五前二直选
		case 21312:
			return "12";// 十一选五前三组选
		case 21313:
			return "10";// 十一选五前三直选
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}

}
