package com.hhly.ticket.service.ticket;

import java.util.List;

import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 出票渠道获取源数据(访问器)，用于计算票的分配
 * @author jiangwei
 * @date 2017年8月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ISource {
	/**
	 * 获取用于计算分配票的元数据
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月3日 下午3:04:38
	 * @param channel 渠道商
	 * @param excisionTime 毫秒
	 * @return
	 */
    int get(ITicketChannel channel);
    /**
     * 添加源数据
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年8月3日 下午3:19:57
     * @param num
     */
    void add(ITicketChannel channel,List<TicketBO> tickets);
}
