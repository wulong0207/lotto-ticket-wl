package com.hhly.ticket.service.entity;

import java.util.ArrayList;
import java.util.List;

import com.hhly.ticket.service.ticket.ITicketChannel;
/**
 * @desc 单个订单可以送票的渠道
 * @author jiangwei
 * @date 2017年8月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class OrderTicketInfoBO {
	
	private List<ITicketChannel> channels;

	private List<TicketBO> tickets;
	
	private String message;

	public OrderTicketInfoBO(List<ITicketChannel> channels) {
		this.channels = new ArrayList<>();
		this.channels.addAll(channels);
		tickets = new ArrayList<>();
	}

	public void addTicket(TicketBO ticketBO) {
		tickets.add(ticketBO);
	}
	
	public void romoveSendChannel(ITicketChannel channel){
		channels.remove(channel);
	}

	
	public List<ITicketChannel> getChannels() {
		return channels;
	}
	
	public List<TicketBO> getTickets() {
		return tickets;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
