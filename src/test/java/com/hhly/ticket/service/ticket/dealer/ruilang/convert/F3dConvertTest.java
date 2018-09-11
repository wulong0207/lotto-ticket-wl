package com.hhly.ticket.service.ticket.dealer.ruilang.convert;

import org.junit.Test;

import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;

public class F3dConvertTest {
	
	private IConvert convert;
	
	@org.junit.Before
	public void Before(){
		convert = new F3dConvert();
	}

	@Test
	public void testHandle() {
		TicketBO bo = new TicketBO();
		bo.setLotteryIssue("2017131");
		bo.setTicketMoney(2.0);
		bo.setMultipleNum(1);
		
		bo.setLotteryChildCode(10501);
		bo.setContentType(1);
		bo.setTicketContent("1|2|3;1|2|3;1|2|3");
		convert.handle(bo);
		System.out.println("直选单式"+bo.getChannelTicketContent());
		bo.setContentType(2);
		bo.setTicketContent("1,2,3,4|1|2,3");
		convert.handle(bo);
		System.out.println("直选复式"+bo.getChannelTicketContent());
		bo.setContentType(6);
		bo.setTicketContent("6");
		convert.handle(bo);
		System.out.println("直选和值"+bo.getChannelTicketContent());
		
		
		bo.setLotteryChildCode(10502);
		bo.setContentType(1);
		bo.setTicketContent("2,2,3;3,3,4");
		convert.handle(bo);
		System.out.println("组三单式"+bo.getChannelTicketContent());
		bo.setContentType(2);
		bo.setTicketContent("0,1,2");
		convert.handle(bo);
		System.out.println("组三复式"+bo.getChannelTicketContent());
		bo.setContentType(6);
		bo.setTicketContent("7");
		convert.handle(bo);
		System.out.println("组三和值"+bo.getChannelTicketContent());
		
		bo.setLotteryChildCode(10503);
		bo.setContentType(1);
		bo.setTicketContent("1,2,3;0,1,2");
		convert.handle(bo);
		System.out.println("组六单式"+bo.getChannelTicketContent());
		bo.setContentType(2);
		bo.setTicketContent("2,3,4,5");
		convert.handle(bo);
		System.out.println("组六复式"+bo.getChannelTicketContent());
		bo.setContentType(6);
		bo.setTicketContent("8");
		convert.handle(bo);
		System.out.println("组六和值"+bo.getChannelTicketContent());
	}

}
