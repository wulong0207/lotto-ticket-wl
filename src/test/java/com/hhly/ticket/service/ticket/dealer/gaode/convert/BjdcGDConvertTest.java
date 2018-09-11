package com.hhly.ticket.service.ticket.dealer.gaode.convert;

import org.junit.Assert;
import org.junit.Test;

import com.hhly.ticket.service.entity.TicketBO;

public class BjdcGDConvertTest {

	BjdcGDConvert convert;

	@org.junit.Before
	public void Before() {
		convert = new BjdcGDConvert();
	}

	@Test
	public void test() {
		// 让球胜平负
		Assert.assertEquals("1:3/2:0", com(convert, "1611011[+1](3)|1611012[+1](0@1.2)^2_1", 30601, 2));
		// 北单上下单双
		Assert.assertEquals("36:31/100:4/101:2",
				com(convert, "17090136(1@1.2,3@1.2)|170901100(2@1.2)|170901101(4@1.2)^3_1", 30602, 4));
		// 北单总进球
		Assert.assertEquals("36:17/100:2/101:12",
				com(convert, "17090136(1,7@1.2)|170901100(2@1.2)|170901101(1@1.2,2@1.2)^3_1", 30603, 8));
		// 北单比分
		Assert.assertEquals("36:9010/100:01/101:1309",
				com(convert, "17090136(90,10)|170901100(01)|170901101(13,09)^3_1", 30604, 8));
		// 北单半全场
		Assert.assertEquals("36:1300/100:30/101:3311",
				com(convert, "17090136(13,00)|170901100(30)|170901101(33,11)^3_1", 30605, 8));
		// 胜负过关
		Assert.assertEquals("36:30/100:0/101:3",
				com(convert, "17090136(3,0)|170901100(0)|170901101(3)^3_1", 30701, 4));
	}

	public static String com(BjdcGDConvert convert, String content, int childCode, int money) {
		TicketBO bo = new TicketBO();
		bo.setTicketContent(content);
		bo.setLotteryChildCode(childCode);
		bo.setMultipleNum(1);
		bo.setTicketMoney(money);
		return convert.getContent(convert.getChildClass(bo), bo);
	}

}
