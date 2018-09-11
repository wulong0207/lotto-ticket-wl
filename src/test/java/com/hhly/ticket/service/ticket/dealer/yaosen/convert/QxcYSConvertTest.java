package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hhly.ticket.base.common.LotteryEnum;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

public class QxcYSConvertTest {

	private static TicketBO bo;
	private static TicketBO bo2;
	private static TicketBO bo3;
	private static TicketBO bo4;
	private static AbstractConvert convert;
	
	@BeforeClass
	public static void setUp() {
		convert = new QxcYSConvert();
		bo = new TicketBO();
		bo.setLotteryCode(LotteryEnum.Lottery.QXC.getName());
		bo.setLotteryChildCode(10701);
		bo.setContentType(1);
		bo.setMultipleNum(1);
		bo.setTicketMoney(4);
		bo.setLottoAdd(0);
		bo.setTicketContent("7|8|9|1|2|3|5;3|5|2|7|9|1|0");
		
		bo2 = new TicketBO();
		bo2.setLotteryCode(LotteryEnum.Lottery.QXC.getName());
		bo2.setLotteryChildCode(10701);
		bo2.setContentType(2);
		bo2.setMultipleNum(1);
		bo2.setTicketMoney(4);
		bo2.setLottoAdd(0);
		bo2.setTicketContent("2,8|3|3|3|8|3,4|4");
		
		bo3 = new TicketBO();
		bo3.setLotteryCode(LotteryEnum.Lottery.QXC.getName());
		bo3.setLotteryChildCode(10701);
		bo3.setContentType(6);
		bo3.setMultipleNum(1);
		bo3.setTicketMoney(4);
		bo3.setLottoAdd(0);
		bo3.setTicketContent("2,8|3|3|3|8|3,4|4");
		
		// 错误的彩种
		bo4 = new TicketBO();
		bo4.setLotteryCode(LotteryEnum.Lottery.DLT.getName());
	}
	
	@Test
	public void testGetContent() {
		// 七星彩 直选
		// 单
		convert.handle(bo);
		assertEquals("1^7/8/9/1/2/3/5,3/5/2/7/9/1/0", bo.getChannelTicketContent());
		// 复
		convert.handle(bo2);
		assertEquals("2^2/8//3//3//3//8//3/4//4", bo2.getChannelTicketContent());
		// 和值抛异常
		assertEquals(false, convert.handle(bo3));
	}
	
	@Test
	public void testGetLotteryCode() {
		assertEquals(String.valueOf(LotteryEnum.Lottery.QXC.getName()), convert.getLotteryCode(bo));
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
