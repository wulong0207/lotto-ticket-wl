package com.hhly.ticket.persistence.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.service.entity.ChannelBO;

public class TicketConfigDaoMapperTest extends DefaultDao{
	@Autowired
	private TicketConfigDaoMapper mapper;

	@Test
	public final void testGetTicketConfig() {
		List<ChannelBO> list =mapper.getTicketConfig(null,null,"1");
		if(list.isEmpty()){
			fail("没有渠道配置");
		}
	}

	@Test
	public final void testGetChannel() {
		mapper.getChannel("2");
	}

}
