package com.hhly.ticket.service.ticket.dealer.saiwei.convert;

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
			bo.setChannelTicketContent(getContent(bo));
			bo.setChannelPlayType(getPlayType(bo));
			bo.setChips(getChips(bo));
		} catch (Exception e) {
			LOGGER.error("格式转换错误", e);
			return false;
		}
		return true;

	}
    private int getChips(TicketBO bo) {
    	int num = 2;
    	if(bo.getLottoAdd() == 1){
    		num = 3;
    	}
    	return ((int) bo.getTicketMoney()) / num;
	}
	/**
     * 获取玩法
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2018年6月23日 上午10:54:37
     * @param bo
     * @return
     */
	public abstract String getPlayType(TicketBO bo); 

	/**
	 * 期号转换
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午3:11:42
	 * @param bo
	 * @return
	 */
	public abstract String getLotteryIssye(TicketBO bo);

	/**
	 * 获取彩种
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午2:14:20
	 * @param lotteryChildCode
	 * @return
	 */
	public abstract String getLotteryCode(TicketBO bo) ;

	/**
	 * 解析投资内容
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月16日 下午5:01:09
	 * @param bo
	 * @return
	 */
	public abstract String getContent(TicketBO bo);
}
