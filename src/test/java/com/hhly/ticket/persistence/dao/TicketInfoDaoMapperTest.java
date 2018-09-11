package com.hhly.ticket.persistence.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.base.vo.AgainOutTicketVO;
import com.hhly.ticket.service.entity.TicketBO;

public class TicketInfoDaoMapperTest extends DefaultDao {

	@Autowired
	private TicketInfoDaoMapper mapper;

	@Test
	public void testListTicketStatus() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		mapper.listTicketStatus(list, 1);
	}

	@Test
	public void testGetTicket() {
		List<String> orders = new ArrayList<>();
		orders.add("D1705111019580100001");
		System.out.println(mapper.getTicket(orders).size());
	}

	@Test
	public void testUpdateTicketSendDealer() {
		List<TicketBO> list = new ArrayList<>();
		for (int i = 1; i < 3; i++) {
			TicketBO bo = new TicketBO();
			list.add(bo);
			bo.setTicketStatus(1);
			bo.setBatchNum("1");
			bo.setBatchNumSeq(i + "");
			bo.setChannelRemark("sdk");
			bo.setReceiptContent("出票商返回");
			bo.setTicketRemark("票备注");
			bo.setId(i);
		}
		mapper.updateTicketSendDealer(list);
	}

	@Test
	public void testListNoAllocationTicket() {
		mapper.listNoAllocationTicket(new Date(),null,null);
	}

	@Test
	public void testUpdateTicketAllocation() {
		List<TicketBO> list = new ArrayList<>();
		for (int i = 1; i < 3; i++) {
			TicketBO bo = new TicketBO();
			list.add(bo);
			bo.setTicketStatus(1);
			bo.setChannelId("2");
			bo.setOutTime(new Date());
			bo.setId(i);
		}
		mapper.updateTicketAllocation(list);
	}

	@Test
	public void testListAllocationTicket() {
		mapper.listAllocationTicket(new Date());
	}

	@Test
	public void testUpdateOutTicket() {
		List<TicketBO> list = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			TicketBO bo = new TicketBO();
			list.add(bo);
			bo.setTicketStatus(i);
			bo.setBatchNum("1");
			bo.setBatchNumSeq(i + "");
			bo.setChannelRemark("sdk");
			bo.setReceiptContent("出票商返回");
			bo.setTicketRemark("票备注");
			bo.setThirdNum("12456");
			bo.setOfficialNum("1");
			bo.setLotteryCode(100);
		}
		mapper.updateOutTicket(list);
	}

	@Test
	public void testListOrderTicketStatus() {
		List<String> betchNum = new ArrayList<>();
		betchNum.add("12345");
		mapper.listOrderTicketStatus(betchNum);
	}

	@Test
	public void testListBatchNumCheckTicket() {
		mapper.listBatchNumCheckTicket(null, null, "O2017041411552102273");
		mapper.listBatchNumCheckTicket(null,Arrays.asList(new String[]{"1000"}) , null);
		mapper.listBatchNumCheckTicket(100, null, null);
		mapper.listBatchNumCheckTicket(100, Arrays.asList(new String[]{"1000"}), "O2017041411552102273");
	}

	@Test
	public void testGetAgainTicket() {
		AgainOutTicketVO againOutTicketVO= new AgainOutTicketVO();
		againOutTicketVO.setBatchNum("SSQ1706011834100100001");
		mapper.getAgainTicket(againOutTicketVO);
		againOutTicketVO= new AgainOutTicketVO();
		againOutTicketVO.setOrderCode("O2017041411552102273");
		mapper.getAgainTicket(againOutTicketVO);
		againOutTicketVO= new AgainOutTicketVO();
		List<String> ticketIds = new ArrayList<>();
		ticketIds.add("140008042");
		ticketIds.add("140008043");
		againOutTicketVO.setTicketIds(ticketIds);
		mapper.getAgainTicket(againOutTicketVO);
	}

}
