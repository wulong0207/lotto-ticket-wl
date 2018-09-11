package com.hhly.ticket.service.ticket.dealer.juxiang.convert.eleven;

import org.junit.Assert;
import org.junit.Test;

import com.hhly.ticket.service.entity.TicketBO;

public class Sd11x5JXConvertTest {
	
	Sd11x5JXConvert convert;

	@org.junit.Before
	public void Before() {
		convert = new Sd11x5JXConvert();
	}

	@Test
	public void test() {
		Assert.assertEquals("01|02|03|;01|02|03|", com(convert, "01,02,03;01,02,03"));
		
		Assert.assertEquals("01|02|03|04|", com(convert, "01,02,03,04"));
		
		Assert.assertEquals("04|*05|06|07|", com(convert, "04#05,06,07"));
		
		Assert.assertEquals("01|", com(convert, "01"));
		
		Assert.assertEquals("01|-02|;03|-04|", com(convert, "01|02;03|04"));
		
		Assert.assertEquals("04|05|-06|07|08|10|", com(convert, "04,05|06,07,08,10"));
		
	}
	
	public static String com(Sd11x5JXConvert convert, String content) {
		TicketBO bo = new TicketBO();
		bo.setTicketContent(content);
		return convert.getContent("", bo);
	}


}
