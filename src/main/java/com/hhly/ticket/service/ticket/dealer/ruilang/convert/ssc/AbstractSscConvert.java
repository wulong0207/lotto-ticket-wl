package com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.AbstractConvert;
/**
 * 
 * @desc 时时彩
 * @author jiangwei
 * @date 2017年7月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractSscConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(2);
	}

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-");
		IPlay play = getPlay(childClass);
		//1：单式；2：复式；6：和值
		switch (bo.getContentType()) {
		case 1:
			sb.append("01-");
			content = play.simple(content);
			break;
		case 2:
			sb.append("02-");
			content = play.complex(content);
			break;
		case 6:
			sb.append("04-");
			content = play.sum(content);
			break;
		default:
			throw new ServiceRuntimeException("不存在内容类型");
		}
		sb.append(content);
		sb.append("-");
		sb.append(bo.getMultipleNum());
		sb.append("-");
		sb.append((int) bo.getTicketMoney());
		return sb.toString();
	}
	/**
	 * 获取玩法装换
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月11日 上午9:58:28
	 * @param childClass
	 * @return
	 */
	public abstract IPlay getPlay(String childClass);

}
