package com.hhly.ticket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.ticket.persistence.dao.TicketConfigDaoMapper;
import com.hhly.ticket.service.IChannelManageService;
import com.hhly.ticket.service.entity.ChannelBO;
@Service
public class ChannelManageServiceImpl implements IChannelManageService {
	@Autowired
	private TicketConfigDaoMapper configDaoMapper;
	@Override
	public List<ChannelBO> getAllChannel(String lotteryCode) {
		return configDaoMapper.getTicketConfig(lotteryCode,null,"1");
	}
	@Override
	public ChannelBO getChannel(String channelId) {
		return configDaoMapper.getChannel(channelId);
	}
	@Override
	public void updateChannelBalance(String channelId, double balance) {
		configDaoMapper.updateChannelBalance(channelId,balance);
	}
	@Override
	public ChannelBO getChannelCheck(String lotteryCode, String channelId) {
		List<ChannelBO> list = configDaoMapper.getTicketConfig(lotteryCode,channelId,null);
		if(list != null){
			return list.get(0);
		}
		return null;
	}

}
