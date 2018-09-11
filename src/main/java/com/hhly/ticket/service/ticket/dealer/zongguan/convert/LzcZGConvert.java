package com.hhly.ticket.service.ticket.dealer.zongguan.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 老足彩格式转换
 * @author jiangwei
 * @date 2018年3月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class LzcZGConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryCode()) {
		case 302://六场半全场
			return "6CBQ";
		case 303://四场进球彩
			return "4CJQ";
		case 304://十四场胜负彩
			return "14CSF";
		case 305://九场胜负彩
			return "SFR9";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在彩种玩法");
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		if(bo.getContentType() == 1){
			return "DS";
		}else if(bo.getContentType() == 2){
			return "FS";
		}
		throw new ServiceRuntimeException("老足彩只能单式或者复试投注");
	}

	@Override
	protected String getContent(TicketBO bo) {
		StringBuilder sb = new StringBuilder();
		for (char ch : bo.getTicketContent().toCharArray()) {
			switch (ch) {
			case '|':
				sb.append(",");
				break;
			case ',':
				break;
			case '_':
				sb.append("-");
				break;
			default:
				sb.append(ch);
				break;
			}
		}
		return sb.toString();
	}

}
