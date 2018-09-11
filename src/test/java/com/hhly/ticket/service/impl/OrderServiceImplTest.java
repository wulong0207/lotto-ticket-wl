package com.hhly.ticket.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.base.common.OrderEnum.OrderStatus;
import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.OrderStatusBO;

public class OrderServiceImplTest extends DefaultDao {
	
	@Autowired
    IOrderService service;
	@Test
	public void testUpdateOrderStatus() {
		Set<String> orders = new HashSet<>();
		orders.add("12345679");
		orders.add("12345679");
		service.updateOrderStatus(orders, OrderStatus.TICKETING.getValue());
		orders.clear();
		orders.add("123456791");
		service.updateOrderStatus(orders, OrderStatus.FAILING_TICKET.getValue());
	}

	@Test
	public void testUpdateOrderStatusOutTicket() {
		List<OrderStatusBO> orders = new ArrayList<>();
		OrderStatusBO bo1 = new OrderStatusBO();
		bo1.setOrderCode("1231");
		bo1.setOrderStatus(6);
		OrderStatusBO bo2 = new OrderStatusBO();
		bo2.setOrderCode("12311");
		bo2.setOrderStatus(7);
		orders.add(bo1);
		orders.add(bo2);
		service.updateOrderStatusOutTicket(orders);
	}

}
