package com.hhly.ticket.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhly.ticket.service.entity.ChannelBO;

/**
 * 出票渠道配置
 * 
 * @desc
 * @author jiangwei
 * @date 2017-2-6
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface TicketConfigDaoMapper {
	/**
	 * 获取渠道配置信息
	 * 
	 * @author jiangwei
	 * @param sendStatus
	 * @param channelId
	 * @Version 1.0
	 * @CreatDate 2017年5月9日 下午4:28:53
	 * @return
	 */
	List<ChannelBO> getTicketConfig(@Param("lotteryCode") String lotteryCode, @Param("channelId") String channelId,
			@Param("sendStatus") String sendStatus);

	/**
	 * 获取渠道商信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月19日 下午12:30:08
	 * @param channelId
	 * @return
	 */
	ChannelBO getChannel(@Param("channelId") String channelId);

	/**
	 * 修改出牌商余额
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月9日 上午9:20:57
	 * @param channelId
	 * @param balance
	 */
	void updateChannelBalance(@Param("channelId") String channelId, @Param("balance") double balance);

}