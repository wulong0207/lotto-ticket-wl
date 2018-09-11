package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hhly.ticket.base.common.LotteryEnum;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

public class DltYSConvertTest {

	private static TicketBO bo;
	private static TicketBO bo2;
	private static TicketBO bo3;
	private static TicketBO bo4;
	private static TicketBO bo5;
	private static AbstractConvert convert;
	
	@BeforeClass
	public static void setUp() {
		 /**
	     *  本系统    ->过关方式^投注号码^追加（大乐透玩法需加,1追加，0不追加）
	     *  单：05,10,19,30,34+03,07 -> 1^05/10/19/30/34//03/07^0(多注用,分隔)
	     *  复：05,10,19,30,34,35+03,07 -> 2^05/10/19/30/34/35//03/07^0
	     *  胆拖：04,09,13,21#03,05,15,22,25,30,32,35+07,11 -> 3^04/09/13/21//03/05/15/22/25/30/32/35,07/11^0
	     */
		convert = new DltYSConvert();
		// 单
		bo = new TicketBO();
		bo.setLotteryCode(LotteryEnum.Lottery.DLT.getName());
		bo.setLotteryChildCode(10201);
		bo.setContentType(1);
		bo.setMultipleNum(1);
		bo.setTicketMoney(4);
		bo.setLottoAdd(0);
		bo.setTicketContent("05,10,19,30,34+03,07");
		// 复
		bo2 = new TicketBO();
		bo2.setLotteryChildCode(10201);
		bo2.setContentType(2);
		bo2.setMultipleNum(1);
		bo2.setTicketMoney(336);
		bo2.setLottoAdd(0);
		bo2.setTicketContent("05,10,19,30,34,35+03,07");
		//胆拖
		bo3 = new TicketBO();
		bo3.setLotteryChildCode(10202);
		bo3.setContentType(3);
		bo3.setMultipleNum(1);
		bo3.setTicketMoney(36);
		bo3.setLottoAdd(0);
		bo3.setTicketContent("04,09,13,21#03,05,15,22,25,30,32,35+07,11");
		
		//和值抛异常
		bo4 = new TicketBO();
		bo4.setLotteryChildCode(10402);
		bo4.setContentType(6);
		bo4.setMultipleNum(1);
		bo4.setTicketMoney(36);
		bo4.setLottoAdd(1);
		bo4.setTicketContent("01,04");
		
		// 错误的彩种
		bo5 = new TicketBO();
		bo5.setLotteryCode(LotteryEnum.Lottery.PL3.getName());
	}
	
	@Test
	public void testGetContent() {
		// 单式
		convert.handle(bo);
		assertEquals("1^05/10/19/30/34//03/07^0", bo.getChannelTicketContent());
		// 复
		convert.handle(bo2);
		assertEquals("2^05/10/19/30/34/35//03/07^0", bo2.getChannelTicketContent());
		// 胆拖
		convert.handle(bo3);
		assertEquals("3^04/09/13/21//03/05/15/22/25/30/32/35,07/11^0", bo3.getChannelTicketContent());
		// 和值抛异常
		assertEquals(false, convert.handle(bo4));
	}
	
	@Test
	public void testGetLotteryCode() {
		assertEquals(String.valueOf(LotteryEnum.Lottery.DLT.getName()), convert.getLotteryCode(bo));
	}

	@Test
	public void testGetPlayType() {
		// 直选单
		assertEquals("1", convert.getPlayType(bo));
		// 复
		assertEquals("2", convert.getPlayType(bo2));
		// 和值
		assertEquals("3", convert.getPlayType(bo3));
	}
	
	@Test(expected=ServiceRuntimeException.class)
	public void testGetPlayType2() {
		// 和值抛异常
		assertEquals("2", convert.getPlayType(bo4));
	}
	
	@Test(expected=ServiceRuntimeException.class)
	public void testGetPlayType3() {
		// 错误的彩种
		assertEquals("2", convert.getPlayType(bo5));
	}
}
