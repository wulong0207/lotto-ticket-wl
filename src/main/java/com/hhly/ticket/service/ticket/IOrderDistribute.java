package com.hhly.ticket.service.ticket;

import java.util.List;

import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 订单分配服务
 * @author jiangwei
 * @date 2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IOrderDistribute {
	/**
	 * 分配订单(所有彩种)
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月4日 下午4:13:28
	 * @param orders
	 */
	void  distribute(List<TicketBO> tickets);
	/**
	 * 分配订单(指定彩种的票信息)
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月6日 下午2:25:07
	 * @param ticket
	 * @param lotteryCode
	 */
	void distribute(List<TicketBO> ticket,int lotteryCode,String channelId);
}
