package com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 广东11选5
 * @author jiangwei
 * @date 2017年7月19日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class GD11X5Convert extends Abstract11X5Convert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "104";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 21002:
			return "02";// 广东十一选五任选二
		case 21003:
			return "03";// 广东十一选五任选三
		case 21004:
			return "04";// 广东十一选五任选四
		case 21005:
			return "05";// 广东十一选五任选五
		case 21006:
			return "06";// 广东十一选五任选六
		case 21007:
			return "07";// 广东十一选五任选七
		case 21008:
			return "08";// 广东十一选五任选八
		case 21009:
			return "01";// 广东十一选五前一
		case 21010:
			return "11";// 广东十一选五前二组选
		case 21011:
			return "09";// 广东十一选五前二直选
		case 21012:
			return "12";// 广东十一选五前三组选
		case 21013:
			return "10";// 广东十一选五前三直选	
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}

}
