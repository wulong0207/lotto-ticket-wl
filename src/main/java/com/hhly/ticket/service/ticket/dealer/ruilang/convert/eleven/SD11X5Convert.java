package com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 山东11选5
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SD11X5Convert extends Abstract11X5Convert {
	
	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 21502:
			return "02";// 山东十一选五任选二
		case 21503:
			return "03";// 山东十一选五任选三
		case 21504:
			return "04";// 山东十一选五任选四
		case 21505:
			return "05";// 山东十一选五任选五
		case 21506:
			return "06";// 山东十一选五任选六
		case 21507:
			return "07";// 山东十一选五任选七
		case 21508:
			return "08";// 山东十一选五任选八
		case 21509:
			return "01";// 山东十一选五前一
		case 21510:
			return "11";// 山东十一选五前二组选
		case 21511:
			return "09";// 山东十一选五前二直选
		case 21512:
			return "12";// 山东十一选五前三组选
		case 21513:
			return "10";// 山东十一选五前三直选	
		case 21514:
			return "13";// 乐二
		case 21515:
			return "14";// 乐三
		case 21516:
			return "15";// 乐四
		case 21517:
			return "16";// 乐五
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}
	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "107";
	}
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}
}
