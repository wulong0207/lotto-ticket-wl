package com.hhly.ticket.service.ticket.dealer.people.cathectic;

import java.util.List;

import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.DealerInfo;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.AbstractDealer;
import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.people.CheckResponse;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 人工出票
 * @author jiangwei
 * @date 2018年6月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class PeopleDealer extends AbstractDealer {

	public static final String SUCCESS = "0";

	public PeopleDealer(ChannelBO bo, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(bo.getDrawerAccount());
		dealerInfo.setAccountPassword(DESUtil.decrypt(bo.getAccountPassword()));
		dealerInfo.setSendUrl(bo.getSendUrl());
		dealerInfo.setSearchMaxTicket(bo.getSearchMaxTicket() > 50 ? 50 : bo.getSearchMaxTicket());
		dealerInfo.setSearchAuto(bo.getSearchAuto());
		dealerInfo.setPreBatch(bo.getPreBatch());
		super.dealerInfo = dealerInfo;
	}

	public PeopleDealer(String drawerAccount, String accountPassword, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(drawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> tickets) {
		for (TicketBO bo : tickets) {
			bo.setBatchNum(dealerInfo.getPreBatch() + TicketUtil.getOrderNo());
			bo.setBatchNumSeq("1");
			doTicketSuccess(bo);
		}
		return true;
	}
	
	@Override
	public double getDealerBalance() {
		return 0;
	}
	
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		return new CheckResponse();
	}
}
