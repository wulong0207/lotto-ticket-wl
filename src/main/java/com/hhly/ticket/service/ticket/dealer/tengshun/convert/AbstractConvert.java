package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import org.apache.log4j.Logger;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.TengShunUtil;

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
		return TengShunUtil.getChannelLotteryCode(bo.getLotteryCode());
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

	/**
	 * 转换普通方式投注
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月19日 上午10:21:52
	 * @param contentType
	 * @return
	 */
	public String getType(int contentType) {
		if (contentType == 1) {
			return "1";
		} else if (contentType == 2) {
			return "2";
		} else if (contentType == 3) {
			return "5";
		} else if (contentType == 6) {
			return "4";
		}
		throw new ServiceRuntimeException("投注类型错误");
	}

	/**
	 * 投注格式结尾
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月19日 上午10:37:07
	 * @param type
	 * @return
	 */
	public StringBuilder contentEnd(String play, String type) {
		StringBuilder sb = new StringBuilder();
		sb.append(":");
		sb.append(play);
		sb.append(":");
		sb.append(type);
		return sb;
	}
	/**
	 * 获取玩法
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月19日 下午3:34:53
	 * @param bo
	 * @return
	 */
	public String play(TicketBO bo) {
		return "1";
	}
}
