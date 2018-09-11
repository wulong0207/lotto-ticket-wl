package com.hhly.ticket.service.ticket.dealer.zongguan.convert;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;

public  abstract class AbstractSportConvert extends AbstractConvert {

	/**
	 * 获取投注内容转换后格式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 上午10:25:54
	 * @param bo
	 * @return
	 */
	@Override
	protected  String getContent(TicketBO bo){
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if (str.length < 2) {
			throw new ServiceRuntimeException("格式错误");
		}
		bo.setChannelPlayType(getWayVie(str[1]));
		return SportSymbolHandler.doJcContent(str[0], bo.getLotteryChildCode(), getSportSymbol());
	}
	
	@Override
	protected String getChildClass(TicketBO bo) {
		return null;
	}
	
	/**
	 * 获取转换格式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 上午11:02:54
	 * @param str
	 * @return
	 */
	protected abstract String getWayVie(String str);
	/**
	 * 获取格式处理
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 上午11:03:05
	 * @return
	 */
	protected abstract ISportSymbol getSportSymbol();

}
