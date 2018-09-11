package com.hhly.ticket.service.ticket.distribute;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.ISource;
import com.hhly.ticket.service.ticket.ITicketChannel;
/**
 * @desc 出票商出票数量
 * @author jiangwei
 * @date 2017年8月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class CountSource implements ISource {
	
	@Autowired
    private MoneySource source;

	@Override
	public int get(ITicketChannel channel) {
		return channel.getTicketCount();
	}

	@Override
	public void add(ITicketChannel channel,List<TicketBO> tickets) {
		channel.addTicketCount(tickets.size());
		source.add(channel, tickets);
	}

}
