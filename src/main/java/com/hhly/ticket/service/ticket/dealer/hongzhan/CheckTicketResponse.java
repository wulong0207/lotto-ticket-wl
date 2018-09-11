package com.hhly.ticket.service.ticket.dealer.hongzhan;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.hongzhan.cathectic.HongZhanDealer;
import com.hhly.ticket.service.ticket.dealer.hongzhan.response.CheckTicket;
/**
 * @desc 检票消息响应
 * @author jiangwei
 * @date 2018年4月13日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CheckTicketResponse implements ICheckResponse{
	
	private String errorcode;
	
	private String errormsg;
	
	private List<CheckTicket> ticketlist;
	
	@Override
	public String getCode() {
		return errorcode;
	}

	@Override
	public String getMessage() {
		return errormsg;
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return ticketlist;
	}

	@Override
	public boolean isSuccess() {
		return HongZhanDealer.SUCCESS.equals(errorcode);
	}

	@Override
	public boolean isExist() {
		return CollectionUtils.isEmpty(getTicket());
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	/**
	 * @return the ticketlist
	 */
	public List<CheckTicket> getTicketlist() {
		return ticketlist;
	}

	/**
	 * @param ticketlist the ticketlist to set
	 */
	public void setTicketlist(List<CheckTicket> ticketlist) {
		this.ticketlist = ticketlist;
	}
     
	
}
