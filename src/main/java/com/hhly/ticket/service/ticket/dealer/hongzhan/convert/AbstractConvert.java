package com.hhly.ticket.service.ticket.dealer.hongzhan.convert;

import org.apache.log4j.Logger;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;


public abstract class AbstractConvert implements IConvert {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractConvert.class);
	
	@Override
	public boolean handle(TicketBO bo) {
		try {
			bo.setChannelLotteryCode(getLotteryCode(bo));
			bo.setChannelLotteryIssue(getLotteryIssye(bo));
			bo.setChannelPlayType(getPlayType(bo));
			bo.setChannelContentType(getChannelContentType(bo));
			bo.setChips(getChips(bo));
			bo.setChannelTicketContent(getContent(bo.getChannelPlayType(), bo));
		} catch (Exception e) {
			LOGGER.error("格式转换错误",e);
			return false;
		}
		return true;
		
	}
	/**
	 * childtype 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年4月14日 上午10:53:59
	 * @param bo
	 * @return
	 */
	public  String getChannelContentType(TicketBO bo){
		return "0";
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
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}
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
	 * 投注方式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年4月17日 上午11:00:20
	 * @param bo
	 * @return
	 */
	protected String getPlayType(TicketBO bo) {
		if(bo.getContentType() == 1){
			return "0";
		}else if(bo.getContentType() == 2){
			return "1";
		}else if(bo.getContentType() == 3){
			return "2";
		}
		throw new ServiceRuntimeException("鸿展玩法错误");
	}
	/**
	 * 获取投注内容转换后格式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 上午10:25:54
	 * @param bo
	 * @return
	 */
	protected  abstract String getContent(String playType,TicketBO bo);
	

	
}
