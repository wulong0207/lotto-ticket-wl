package com.hhly.ticket.service.ticket;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;

public class SendMessageTest extends DefaultDao {
	
	@Autowired
	SendMessage sendMessage;
	
	@Test
	public void testSendOuting() {
		List<String> list = new ArrayList<>();
		list.add("123456791");
		sendMessage.sendOuting(list);
	}

	@Test
	public void testSendOutFail() {
		List<String> list = new ArrayList<>();
		list.add("123456792");
		sendMessage.sendOutFail(list);
	}

	@Test
	public void testSendOutSuccess() {
		List<String> list = new ArrayList<>();
		list.add("123456793");
		sendMessage.sendOutSuccess(list);
	}

}
