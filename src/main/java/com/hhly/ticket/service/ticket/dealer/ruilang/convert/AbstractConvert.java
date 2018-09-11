package com.hhly.ticket.service.ticket.dealer.ruilang.convert;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;

public abstract class AbstractConvert implements IConvert {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractConvert.class);
	
	@Override
	public boolean handle(TicketBO bo) {
		String content = bo.getTicketContent();
		if(StringUtils.isBlank(content)){
			return false;
		}
		try {
			String childClass = getChildClass(bo);
			bo.setChannelTicketContent(getContent(childClass,bo));
			String lotteryCode = getLotteryCode(bo);
			bo.setChannelLotteryCode(lotteryCode);
			String issue = getLotteryIssye(bo);
			bo.setChannelLotteryIssue(issue);
		} catch (ServiceRuntimeException e) {
			LOGGER.info(e.getMsg()+"|"+bo.toString());
			bo.setTicketRemark(e.getMessage());
			return false;
		}
		return true;
	}
	/**
	 * 期号转换
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午3:11:42
	 * @param bo
	 * @return
	 */
	protected abstract String getLotteryIssye(TicketBO bo);
	/**
	 * 获取彩种
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午2:14:20
	 * @param lotteryChildCode
	 * @return
	 */
	protected abstract String getLotteryCode(TicketBO bo);
	/**
	 * 获取自子类型
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 上午10:11:22
	 * @param lotteryChildCode
	 * @return
	 */
	protected abstract String getChildClass(TicketBO bo);
	/**
	 * 获取投注内容转换后格式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 上午10:25:54
	 * @param bo
	 * @return
	 */
	protected  abstract String getContent(String childClass,TicketBO bo);
}
