package com.hhly.ticket.service.ticket.dealer.zongguan.convert;

import org.apache.log4j.Logger;

import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;

public abstract class AbstractConvert implements IConvert {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractConvert.class);
	
	@Override
	public boolean handle(TicketBO bo) {
		try {
			bo.setChannelLotteryCode(getLotteryCode(bo));
			bo.setChannelLotteryIssue(getLotteryIssye(bo));
			bo.setChannelPlayType(getChildClass(bo));
			bo.setChips(getChips(bo));
			bo.setChannelTicketContent(getContent(bo));
		} catch (Exception e) {
			LOGGER.error("格式转换错误",e);
			return false;
		}
		return true;
		
	}
	/**
	 * 获取注数
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 下午12:26:40
	 * @param bo
	 * @return
	 */
	protected int getChips(TicketBO bo) {
		return ((int) bo.getTicketMoney()) / bo.getMultipleNum() / 2;
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
	 * 解析投资内容
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月16日 下午5:01:09
	 * @param bo
	 * @return
	 */
	protected  abstract String getContent(TicketBO bo);
}
