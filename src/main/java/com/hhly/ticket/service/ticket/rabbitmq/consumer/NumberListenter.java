package com.hhly.ticket.service.ticket.rabbitmq.consumer;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hhly.ticket.base.common.OrderEnum.OrderStatus;
import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.IOrderDistribute;
/**
 * @desc 数字彩订单列队监听
 * @author jiangwei
 * @date 2017年5月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Component
public class NumberListenter implements MessageListener {
	
	private static final Logger LOGGER = Logger.getLogger(NumberListenter.class);
	@Autowired
	private IOrderDistribute distribute;

	@Autowired
	private IOrderService orderService;

	@RabbitListener(queues="sendticket_queue", containerFactory="numberRabbitmqContainerFactory" , priority = "10" )
	public void onMessage(Message message) {
		String result = "";
		try {
			result = new String(message.getBody(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(message.toString(),e);
		}
		if(StringUtils.isEmpty(result)){
			return;
		}
		String [] orders = StringUtils.tokenizeToStringArray(result, SymbolConstants.COMMA);
		if(orders.length == 0){
			return;
		}
		LOGGER.info("mq接受到彩票订单"+result);
		List<String> orderList = Arrays.asList(orders);
		try {
			List<TicketBO> tickets = orderService.getTicket(orderList);
			if(CollectionUtils.isEmpty(tickets)){
				LOGGER.info("mq接受订单不存在票信息："+result);
			}
			// 分配票
			distribute.distribute(tickets);
		} catch (Exception e) {
			LOGGER.error("处理送票订单异常" + result,e);
			String reamk = e.getMessage();
			if(reamk.length() > 90){
				reamk = reamk.substring(0, 90);
			}
			Set<String> errorOrders = new HashSet<>();
			errorOrders.addAll(orderList);
			orderService.updateOrderStatus(errorOrders, OrderStatus.FAILING_TICKET.getValue());
			orderService.updateTicketStatusByOrderCode(errorOrders,TicketStatus.SEND_FAIL.getValue(),reamk);
			
		}
	}

}
