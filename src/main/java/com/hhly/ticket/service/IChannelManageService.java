package com.hhly.ticket.service;

import java.util.List;

import com.hhly.ticket.service.entity.ChannelBO;

/**
 * @desc 渠道管理服务
 * @author jiangwei
 * @date 2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IChannelManageService {
	 /**
	  * 获取所有渠道
	  * @author jiangwei
	  * @Version 1.0
	  * @CreatDate 2017年5月5日 下午4:22:33
	  * @return
	  */
	 List<ChannelBO> getAllChannel(String lotteryCode);
	 /**
	  * 获取渠道商信息
	  * @author jiangwei
	  * @Version 1.0
	  * @CreatDate 2017年5月19日 下午12:29:18
	  * @param channelId
	  * @return
	  */
	 ChannelBO getChannel(String channelId);
	 /**
	  * 修改出票商余额
	  * @author jiangwei
	  * @Version 1.0
	  * @CreatDate 2017年8月9日 上午9:20:16
	  * @param channelId
	  * @param balance
	  */
	 void updateChannelBalance(String channelId, double balance);
	 /**
	  * 获取检票渠道
	  * @author jiangwei
	  * @Version 1.0
	  * @CreatDate 2018年7月10日 上午10:13:24
	  * @param lotteryCode
	  * @param channelId
	  * @return
	  */
	ChannelBO getChannelCheck(String lotteryCode, String channelId);
	 
}
