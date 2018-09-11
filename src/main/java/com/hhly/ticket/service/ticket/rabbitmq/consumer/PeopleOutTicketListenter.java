package com.hhly.ticket.service.ticket.rabbitmq.consumer;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hhly.ticket.service.IOrderService;


@Component
public class PeopleOutTicketListenter implements MessageListener{
	private static final Logger LOGGER = Logger.getLogger(PeopleOutTicketListenter.class);

	@Autowired
	private IOrderService orderService;
	
	@RabbitListener(queues="people_out_ticket", containerFactory="peopleoutRabbitmqContainerFactory")
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
		try {
			orderService.updateSynchronizedOrderStatusById(result);	
		} catch (Exception e) {
			LOGGER.info("同步订单状态错误",e);
		}
		
	}
}
