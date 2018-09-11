package com.hhly.ticket.service.ticket.distribute;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.ISource;
import com.hhly.ticket.service.ticket.ITicketChannel;
/**
 * @desc 出票商出票总金额
 * @author jiangwei
 * @date 2017年8月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class MoneySource implements ISource {

	@Override
	public int get(ITicketChannel channel) {
		return channel.getTicketMoney();
	}

	@Override
	public void add(ITicketChannel channel, List<TicketBO> tickets) {
		int money = 0;
		for (TicketBO ticketBO : tickets) {
			money += (int)ticketBO.getTicketMoney();
		}
		channel.addTicketMoney(money);
	}

}
