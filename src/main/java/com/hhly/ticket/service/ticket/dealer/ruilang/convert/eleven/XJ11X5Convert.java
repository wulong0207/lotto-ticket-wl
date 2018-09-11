package com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

public class XJ11X5Convert extends Abstract11X5Convert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "102";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 27302:
			return "02";// 十一选五任选二
		case 27303:
			return "03";// 十一选五任选三
		case 27304:
			return "04";// 十一选五任选四
		case 27305:
			return "05";// 十一选五任选五
		case 27306:
			return "06";// 十一选五任选六
		case 27307:
			return "07";// 十一选五任选七
		case 27308:
			return "08";// 十一选五任选八
		case 27309:
			return "01";// 十一选五前一
		case 27310:
			return "11";// 十一选五前二组选
		case 27311:
			return "09";// 十一选五前二直选
		case 27312:
			return "12";// 十一选五前三组选
		case 27313:
			return "10";// 十一选五前三直选
		case 27314:
			return "13";// 乐二
		case 27315:
			return "14";// 乐三
		case 27316:
			return "15";// 乐四
		case 27317:
			return "16";// 乐五
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}

}
