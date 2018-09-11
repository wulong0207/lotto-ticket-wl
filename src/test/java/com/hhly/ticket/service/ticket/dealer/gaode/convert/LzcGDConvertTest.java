package com.hhly.ticket.service.ticket.dealer.gaode.convert;

import org.junit.Assert;
import org.junit.Test;

import com.hhly.ticket.service.entity.TicketBO;

public class LzcGDConvertTest {
	LzcGDConvert convert;

	@org.junit.Before
	public void Before() {
		convert = new LzcGDConvert();
	}
	
	@Test
	public void test() {
		Assert.assertEquals("3/1/0/0/0/1/1/3/3/3/0/1/0/3", com(convert, "3|1|0|0|0|1|1|3|3|3|0|1|0|3", 304, 2));
		
		Assert.assertEquals("3/1/0/0/0/1/1/3/3/3/0/1/0/3|3/1/0/0/0/1/1/3/3/3/0/1/0/3", com(convert, "3|1|0|0|0|1|1|3|3|3|0|1|0|3;3|1|0|0|0|1|1|3|3|3|0|1|0|3", 304, 2));
		
		Assert.assertEquals("30/1/0/0/0/10/10/3/31/3/0/10/0/3", com(convert, "3,0|1|0|0|0|1,0|1,0|3|3,1|3|0|1,0|0|3", 304,32));
		
		Assert.assertEquals("/1/0//0/1/1/3/3/3//1//", com(convert, "_|1|0|_|0|1|1|3|3|3|_|1|_|_", 305, 2));
		
	}
	public static String com(LzcGDConvert convert, String content, int lotteryCode, int money) {
		TicketBO bo = new TicketBO();
		bo.setTicketContent(content);
		bo.setLotteryCode(lotteryCode);
		bo.setMultipleNum(1);
		bo.setTicketMoney(money);
		return convert.getContent(convert.getChildClass(bo), bo);
	}
}
