package com.hhly.ticket.service.ticket.dealer.gaode.convert;

import org.apache.log4j.Logger;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;
public abstract class AbstractConvert implements IConvert {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractConvert.class);
	
	@Override
	public boolean handle(TicketBO bo) {
		try {
			bo.setChannelLotteryCode(getLotteryCode(bo));
			bo.setChannelLotteryIssue(getLotteryIssye(bo));
			bo.setChannelPlayType(getChildClass(bo));
			bo.setChips(getChips(bo));
			bo.setChannelTicketContent(getContent(bo.getChannelPlayType(), bo));
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
	 * 获取投注内容转换后格式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 上午10:25:54
	 * @param bo
	 * @return
	 */
	protected  String getContent(String childClass,TicketBO bo){
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if (str.length < 2) {
			throw new ServiceRuntimeException("格式错误");
		}
		bo.setChannelPassMode(getWayVie(str[1]));
		return SportSymbolHandler.doJcContent(str[0], bo.getLotteryChildCode(), getSportSymbol());
	}
	/**
	 * 获取转换格式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 上午11:02:54
	 * @param str
	 * @return
	 */
	protected  String getWayVie(String str){
		return null;
	}
	/**
	 * 获取格式处理
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 上午11:03:05
	 * @return
	 */
	protected  ISportSymbol getSportSymbol(){
		return null;
	};
	
}
