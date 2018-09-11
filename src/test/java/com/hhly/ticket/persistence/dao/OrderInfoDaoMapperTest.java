package com.hhly.ticket.persistence.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.service.entity.OrderStatusBO;


public class OrderInfoDaoMapperTest extends DefaultDao{
	
	@Autowired
    private OrderInfoDaoMapper orderInfoDaoMapper;
	
	@Test
	public void testUpdateOrderStatus() {
		Set<String> set = new HashSet<>();
		set.add("D1705151804000100002");
		orderInfoDaoMapper.updateOrderStatus(set,5);
	}

	@Test
	public void testUpdateOrderStatusOutTicket() {
		List<OrderStatusBO> orders = new ArrayList<>();
		OrderStatusBO bo = new OrderStatusBO();
		bo.setOrderCode("D17101415150917300041");
		bo.setOrderStatus(5);
		bo.setComeOutTime(new Date());
		orders.add(bo);
		OrderStatusBO bo1 = new OrderStatusBO();
		bo1.setOrderCode("D17101415520717300045");
		bo1.setOrderStatus(4);
		orders.add(bo1);
		OrderStatusBO bo2 = new OrderStatusBO();
		bo2.setOrderCode("D17101415525017200042");
		bo2.setOrderStatus(6);
		bo2.setComeOutTime(new Date());
		orders.add(bo2);
		orderInfoDaoMapper.updateOrderStatusOutTicket(orders);
	}

}
