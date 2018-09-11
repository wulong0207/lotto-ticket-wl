package com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport;

import org.junit.Assert;
import org.junit.Test;

import com.hhly.ticket.service.entity.TicketBO;

public class BjdcConvertTest {
	
	BjdcConvert convert;
	
    @org.junit.Before
	public void Before(){
    	convert = new BjdcConvert();
	}
    
	@Test
	public void test() {
		//让球胜平负
		Assert.assertEquals("00-22-1:[胜]/2:[负]-1-2",com(convert, "1611011[+1](3@1.2)|1611012[+1](0@1.2)^2_1", 30601, 2));
		//北单上下单双
		Assert.assertEquals("00-24-36:[上+单,下+单]/100:[上+双]/101:[下+双]-1-4",com(convert, "17090136(1@1.2,3@1.2)|170901100(2@1.2)|170901101(4@1.2)^3_1", 30602, 4));
		// 北单总进球
		Assert.assertEquals("00-24-36:[1,7+]/100:[2]/101:[1,2]-1-8",com(convert, "17090136(1@1.2,7@1.2)|170901100(2@1.2)|170901101(1@1.2,2)^3_1", 30603, 8));
		//北单比分
		Assert.assertEquals("00-24-36:[胜其他,1:0]/100:[0:1]/101:[1:3,负其他]-1-8",com(convert, "17090136(90@1.2,10@1.2)|170901100(01@1.2)|170901101(13@1.2,09)^3_1", 30604, 8));
		// 北单半全场
		Assert.assertEquals("00-24-36:[平-胜,负-负]/100:[胜-负]/101:[胜-胜,平-平]-1-8",com(convert, "17090136(13,00)|170901100(30)|170901101(33,11)^3_1", 30605, 8));
		//胜负过关
		Assert.assertEquals("00-24-36:[胜,负]/100:[负]/101:[胜]-1-4",com(convert, "17090136(3,0)|170901100(0)|170901101(3)^3_1", 30701, 4));
	}
	
	public static String com(BjdcConvert convert,String content,int childCode,int money){
		TicketBO bo = new TicketBO();
		bo.setTicketContent(content);
		bo.setLotteryChildCode(childCode);
		bo.setMultipleNum(1);
		bo.setTicketMoney(money);
		return convert.getContent(convert.getChildClass(bo), bo);
	}

}
