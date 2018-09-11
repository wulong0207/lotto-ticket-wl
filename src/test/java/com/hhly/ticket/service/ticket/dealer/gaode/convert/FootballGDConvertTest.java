package com.hhly.ticket.service.ticket.dealer.gaode.convert;

import org.junit.Assert;
import org.junit.Test;

import com.hhly.ticket.service.entity.TicketBO;

public class FootballGDConvertTest {
	FootballGDConvert convert;

	@org.junit.Before
	public void Before() {
		convert = new FootballGDConvert();
	}
	@Test
	public void test() {
		Assert.assertEquals("1001:3/1002:1/1003:0", com(convert, "1611281001[+1](3@1.57)|1611281002[+1](1@1.89)|1611281003[+1](0@4.21)^3_1^99", 30002, 2));
		
		Assert.assertEquals("1001:3_2/1002:0_1/1003:0_4/1004:3_2", com(convert, "1611281001_R[+1](3@1.57)|1611281002_S(0@4.21)|1611281003_Z(0@4.21)|1611281004_R[-2](3@3.33)^4_1^99", 30001, 2));
		
		Assert.assertEquals("1001:31_2/1002:01_1/1003:0_4/1004:3_2", com(convert, "1611281001_R[+1](3@1.57,1@1.2)|1611281002_S(0@4.21,1@3.5)|1611281003_Z(0@4.21)|1611281004_R[-2](3@3.33)^4_1^99", 30001, 8));
	}


	public static String com(FootballGDConvert convert, String content, int childCode, int money) {
		TicketBO bo = new TicketBO();
		bo.setTicketContent(content);
		bo.setLotteryChildCode(childCode);
		bo.setMultipleNum(1);
		bo.setTicketMoney(money);
		return convert.getContent(convert.getChildClass(bo), bo);
	}
}
