package com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.change.DirectPlay;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.change.SizePlay;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.change.ThreeGroupPlay;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.change.TwoGroupPlay;

/**
 * @desc 重庆时时彩
 * @author jiangwei
 * @date 2017年7月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CqsscConvert extends AbstractSscConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "018";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 20101:// 重庆时时彩五星直选
			return "05";
		case 20102:// 重庆时时彩五星通选
			return "13";
		case 20103:// 重庆时时彩三星直选
			return "03";
		case 20104:// 重庆时时彩三星组三
			return "07";
		case 20105:// 重庆时时彩三星组六
			return "08";
		case 20106:// 重庆时时彩二星直选
			return "02";
		case 20107:// 重庆时时彩二星组选
			return "06";
		case 20108:// 重庆时时彩一星
			return "01";
		case 20109:// 重庆时时彩大小单双
			return "23";
		default:
			throw new ServiceRuntimeException("重庆时时彩子玩法错误！");
		}
	}

	@Override
	public IPlay getPlay(String childClass) {
		IPlay play = null;
		switch (childClass) {
		case "01":
		case "02":
		case "03":
		case "05":
		case "13":
			play = new DirectPlay();
			break;
		case "06":
			play = new TwoGroupPlay();
			break;
		case "07":
		case "08":
			play = new ThreeGroupPlay();
			break;
		case "23":
			play = new SizePlay();
			break;
		default:
			throw new ServiceRuntimeException("重庆时时彩玩法格式转换错误");
		}
		return play;
	} 

}
