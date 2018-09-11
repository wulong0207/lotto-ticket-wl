package com.hhly.ticket.service.ticket.dealer;

import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 票装换接口
 * @author jiangwei
 * @date 2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IConvert {
	/**
	 * 装换处理
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月4日 下午6:21:10
	 * @param bo
	 * @return
	 */
    boolean handle(TicketBO bo);
}
