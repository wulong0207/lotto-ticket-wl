package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import org.junit.Assert;
import org.junit.Test;

import com.hhly.ticket.service.entity.TicketBO;

public class SportYSConvertTest {
	SportYSConvert convert;

	@org.junit.Before
	public void Before() {
		convert = new SportYSConvert();
	}
	@Test
	public void test() {
		Assert.assertEquals("3*1^F201611281001,3//F201611281002,1//F201611281003,0", com(convert, "1611281001[+1](3@1.57)|1611281002[+1](1@1.89)|1611281003[+1](0@4.21)^3_1^99", 30002, 2));
		
		Assert.assertEquals("4*1^F201611281001,3-105//F201611281002,0-101//F201611281003,0-103//F201611281004,3-105", com(convert, "1611281001_R[+1](3@1.57)|1611281002_S(0@4.21)|1611281003_Z(0@4.21)|1611281004_R[-2](3@3.33)^4_1^99", 30001, 2));
		
		Assert.assertEquals("4*1^F201611281001,3/1-105//F201611281002,0/1-101//F201611281003,0-103//F201611281004,3-105", com(convert, "1611281001_R[+1](3@1.57,1@1.2)|1611281002_S(0@4.21,1@3.5)|1611281003_Z(0@4.21)|1611281004_R[-2](3@3.33)^4_1^99", 30001, 8));
	}


	public static String com(SportYSConvert convert, String content, int childCode, int money) {
		TicketBO bo = new TicketBO();
		bo.setTicketContent(content);
		bo.setLotteryChildCode(childCode);
		bo.setMultipleNum(1);
		bo.setTicketMoney(money);
		return convert.getContent(convert.getPlayType(bo), bo);
	}
}
