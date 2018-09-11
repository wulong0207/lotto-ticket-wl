package com.hhly.ticket.service.ticket.dealer.zhongle.convert;

import org.apache.log4j.Logger;

import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;
import com.hhly.ticket.service.ticket.dealer.zhongle.ZhongLeUtil;

public abstract class AbstractConvert implements IConvert {

	private static final Logger LOGGER = Logger.getLogger(AbstractConvert.class);

	@Override
	public boolean handle(TicketBO bo) {
		try {
			bo.setChannelLotteryCode(getLotteryCode(bo));
			bo.setChannelLotteryIssue(getLotteryIssye(bo));
			bo.setChannelTicketContent(getContent(bo));
		} catch (Exception e) {
			LOGGER.error("格式转换错误", e);
			return false;
		}
		return true;

	}

	/**
	 * 期号转换
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午3:11:42
	 * @param bo
	 * @return
	 */
	protected  String getLotteryIssye(TicketBO bo){
		return bo.getLotteryIssue();
	}

	/**
	 * 获取彩种
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午2:14:20
	 * @param lotteryChildCode
	 * @return
	 */
	protected String getLotteryCode(TicketBO bo) {
		return ZhongLeUtil.getChannelLotteryCode(bo.getLotteryCode());
	};

	/**
	 * 解析投资内容
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月16日 下午5:01:09
	 * @param bo
	 * @return
	 */
	protected abstract String getContent(TicketBO bo);
}
