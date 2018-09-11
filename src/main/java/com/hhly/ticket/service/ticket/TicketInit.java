package com.hhly.ticket.service.ticket;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 项目重启初始化
 * @author jiangwei
 * @date 2017年5月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Component
public class TicketInit implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger LOGGER = Logger.getLogger(TicketInit.class);
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderDistribute distribute;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
				    init();
				}
			}, 30000);
		}
	}

	/**
	 * 初始化把所有已分配订单加载到内存中
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 下午4:29:53
	 */
	private void init() {
		LOGGER.info("初始化已分配的订单到缓存中");
		if(orderService.checkTask("ticket","init")){
			LOGGER.info("执行分配（初始化已分配的订单到缓存中）");
			try {
				//重启将不由本系统初始化已分配的订单
				List<TicketBO> tickets = orderService.listAllocationTicket(null);
				distribute.distribute(tickets);
			} catch (Exception e) {
				LOGGER.info("初始化已分配的订单到缓存失败",e);
			}
		}else{
			LOGGER.info("其它机器已执行（初始化已分配的订单到缓存中）");
		}
	}
}
