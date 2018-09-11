package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hhly.ticket.base.common.LotteryEnum;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

public class Pl3YSConvertTest {

	private static TicketBO bo;
	private static TicketBO bo2;
	private static TicketBO bo3;
	private static TicketBO bo4;
	private static TicketBO bo5;
	private static TicketBO bo6;
	private static TicketBO bo7;
	private static TicketBO bo8;
	private static TicketBO bo9;
	private static TicketBO bo10;
	private static AbstractConvert convert;
	
	@BeforeClass
	public static void setUp() {
		convert = new Pl3YSConvert();
		// 排列3 直选
		// 单
		bo = new TicketBO();
		bo.setLotteryCode(LotteryEnum.Lottery.PL3.getName());
		bo.setLotteryChildCode(10401);
		bo.setContentType(1);
		bo.setMultipleNum(1);
		bo.setTicketMoney(4);
		bo.setLottoAdd(0);
		bo.setTicketContent("1|0|0;0|0|1;0|1|0");
		// 复
		bo2 = new TicketBO();
		bo2.setLotteryChildCode(10401);
		bo2.setContentType(2);
		bo2.setMultipleNum(1);
		bo2.setTicketMoney(336);
		bo2.setLottoAdd(0);
		bo2.setTicketContent("7|2|8,9");
		//和值
		bo3 = new TicketBO();
		bo3.setLotteryChildCode(10401);
		bo3.setContentType(6);
		bo3.setMultipleNum(1);
		bo3.setTicketMoney(36);
		bo3.setLottoAdd(1);
		bo3.setTicketContent("01,04");
		
		// 排列3 组三 
		// 单
		bo4 = new TicketBO();
		bo4.setLotteryChildCode(10402);
		bo4.setContentType(1);
		bo4.setMultipleNum(1);
		bo4.setTicketMoney(4);
		bo4.setLottoAdd(0);
		bo4.setTicketContent("0,0,1;8,9,9");
		// 复
		bo5 = new TicketBO();
		bo5.setLotteryChildCode(10402);
		bo5.setContentType(2);
		bo5.setMultipleNum(1);
		bo5.setTicketMoney(336);
		bo5.setLottoAdd(0);
		bo5.setTicketContent("4,8");
		//和值抛异常
		bo6 = new TicketBO();
		bo6.setLotteryChildCode(10402);
		bo6.setContentType(6);
		bo6.setMultipleNum(1);
		bo6.setTicketMoney(36);
		bo6.setLottoAdd(1);
		bo6.setTicketContent("01,04");
		
		// 排列3 组六 
		// 单
		bo7 = new TicketBO();
		bo7.setLotteryChildCode(10403);
		bo7.setContentType(1);
		bo7.setMultipleNum(1);
		bo7.setTicketMoney(4);
		bo7.setLottoAdd(0);
		bo7.setTicketContent("0,6,9;0,3,9");
		// 复
		bo8 = new TicketBO();
		bo8.setLotteryChildCode(10403);
		bo8.setContentType(2);
		bo8.setMultipleNum(1);
		bo8.setTicketMoney(336);
		bo8.setLottoAdd(0);
		bo8.setTicketContent("1,3,8,9");
		//和值抛异常
		bo9 = new TicketBO();
		bo9.setLotteryChildCode(10403);
		bo9.setContentType(6);
		bo9.setMultipleNum(1);
		bo9.setTicketMoney(36);
		bo9.setLottoAdd(1);
		bo9.setTicketContent("1,3,8,9");
		
		// 错误的彩种
		bo10 = new TicketBO();
		bo10.setLotteryCode(LotteryEnum.Lottery.DLT.getName());
	}
	
	/**
     *  本系统    ->过关方式^投注号码^追加（大乐透玩法需加,1追加，0不追加）
     *  直选单式示例：1|0|0;0|0|1;0|1|0 -> 11^1/0/0,0/0/1,0/1/0
     *  组三单式示例：0,0,1;8,9,9 -> 12^0/0/1,8/9/9
     *  组六单式示例：0,6,9;0,3,9 -> 13^0/6/9,0/3/9
     *  直选复式示例：7|2|8,9 -> 21^7//2//8/9
     *  组三复式示例：4,8 -> 22^4/8
     *  组六复式示例：1,3,8,9 -> 23^1/3/8/9
     *  直选和值示例：01,04 -> 31^01/04
     */
	@Test
	public void testGetContent() {
		// 直选单式
		convert.handle(bo);
		assertEquals("11^1/0/0,0/0/1,0/1/0", bo.getChannelTicketContent());
		// 复
		convert.handle(bo2);
		assertEquals("21^7//2//8/9", bo2.getChannelTicketContent());
		// 和值
		convert.handle(bo3);
		assertEquals("31^01/04", bo3.getChannelTicketContent());
		
		// 组选三单式
		convert.handle(bo4);
		assertEquals("12^0/0/1,8/9/9", bo4.getChannelTicketContent());
		// 复
		convert.handle(bo5);
		assertEquals("22^4/8", bo5.getChannelTicketContent());
		// 和值
		assertEquals(false, convert.handle(bo6));
		
		// 组选六单式
		convert.handle(bo7);
		assertEquals("13^0/6/9,0/3/9", bo7.getChannelTicketContent());
		// 复
		convert.handle(bo8);
		assertEquals("23^1/3/8/9", bo8.getChannelTicketContent());
		// 和值
		assertEquals(false, convert.handle(bo9));
	}
	
	@Test
	public void testGetLotteryCode() {
		assertEquals(String.valueOf(LotteryEnum.Lottery.PL3.getName()), convert.getLotteryCode(bo));
	}

	@Test
	public void testGetPlayType() {
		// 直选单
		assertEquals("11", convert.getPlayType(bo));
		// 复
		assertEquals("21", convert.getPlayType(bo2));
		// 和值
		assertEquals("31", convert.getPlayType(bo3));
		// 组选三单
		assertEquals("12", convert.getPlayType(bo4));
		// 复
		assertEquals("22", convert.getPlayType(bo5));
		// 组选六单
		assertEquals("13", convert.getPlayType(bo7));
		// 复
		assertEquals("23", convert.getPlayType(bo8));
	}
	
	@Test(expected=ServiceRuntimeException.class)
	public void testGetPlayType2() {
		// 和值抛异常
		assertEquals("2", convert.getPlayType(bo6));
	}
	
	@Test(expected=ServiceRuntimeException.class)
	public void testGetPlayType3() {
		// 错误的彩种
		assertEquals("2", convert.getPlayType(bo9));
	}

}
