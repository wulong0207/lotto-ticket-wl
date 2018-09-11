package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hhly.ticket.base.common.LotteryEnum;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

public class Pl5YSConvertTest {

	private static TicketBO bo;
	private static TicketBO bo2;
	private static TicketBO bo3;
	private static TicketBO bo4;
	private static AbstractConvert convert;
	
	@BeforeClass
	public static void setUp() {
		convert = new Pl5YSConvert();
		bo = new TicketBO();
		bo.setLotteryCode(LotteryEnum.Lottery.PL5.getName());
		bo.setLotteryChildCode(10301);
		bo.setContentType(1);
		bo.setMultipleNum(1);
		bo.setTicketMoney(4);
		bo.setLottoAdd(0);
		bo.setTicketContent("6|6|2|3|2;7|6|7|6|7;1|3|6|3|1");
		
		bo2 = new TicketBO();
		bo2.setLotteryCode(LotteryEnum.Lottery.QXC.getName());
		bo2.setLotteryChildCode(10301);
		bo2.setContentType(2);
		bo2.setMultipleNum(1);
		bo2.setTicketMoney(4);
		bo2.setLottoAdd(0);
		bo2.setTicketContent("1|8,9|4,5|6|2,7");
		
		bo3 = new TicketBO();
		bo3.setLotteryCode(LotteryEnum.Lottery.QXC.getName());
		bo3.setLotteryChildCode(10301);
		bo3.setContentType(6);
		bo3.setMultipleNum(1);
		bo3.setTicketMoney(4);
		bo3.setLottoAdd(0);
		bo3.setTicketContent("1|8,9|4,5|6|2,7");
		
		// 错误的彩种
		bo4 = new TicketBO();
		bo4.setLotteryCode(LotteryEnum.Lottery.DLT.getName());
	}
	
	@Test
	public void testGetContent() {
		// 七星彩 直选
		// 单
		convert.handle(bo);
		assertEquals("1^6/6/2/3/2,7/6/7/6/7,1/3/6/3/1", bo.getChannelTicketContent());
		// 复
		convert.handle(bo2);
		assertEquals("2^1//8/9//4/5//6//2/7", bo2.getChannelTicketContent());
		// 和值抛异常
		assertEquals(false, convert.handle(bo3));
	}
	
	@Test
	public void testGetLotteryCode() {
		assertEquals(String.valueOf(LotteryEnum.Lottery.PL5.getName()), convert.getLotteryCode(bo));
	}

	@Test
	public void testGetPlayType() {
		// 单
		assertEquals("1", convert.getPlayType(bo));
		// 复
		assertEquals("2", convert.getPlayType(bo2));

	}
	
	@Test(expected=ServiceRuntimeException.class)
	public void testGetPlayType2() {
		// 和值抛异常
		assertEquals("2", convert.getPlayType(bo3));
	}
	
	@Test(expected=ServiceRuntimeException.class)
	public void testGetPlayType3() {
		// 错误的彩种
		assertEquals("2", convert.getPlayType(bo4));
	}

}
