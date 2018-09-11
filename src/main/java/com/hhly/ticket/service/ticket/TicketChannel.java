package com.hhly.ticket.service.ticket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.hhly.ticket.base.common.OrderEnum.OrderStatus;
import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.CheckBO;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;
import com.hhly.ticket.service.ticket.dealer.IDealer;

/**
 * 
 * @desc 渠道出票中间类
 * @author jiangwei
 * @date 2017年5月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class TicketChannel extends AbstractTicketChannel {
	private static final Logger LOGGER = Logger.getLogger(TicketChannel.class);
	/**
	 * 票服务
	 */
	protected IOrderService orderService;

	/**
	 * 未送票
	 */
	private Map<String, Set<TicketBO>> notSendTicketMap = new ConcurrentHashMap<>();

	/**
	 * 构造函数，为每一个出票商启动一个票打包线程
	 * 
	 * @param convert
	 * @param dealer
	 * @param channelBO
	 * @param orderService
	 */
	public TicketChannel(IConvert convert, IDealer dealer, ChannelBO channelBO, IOrderService orderService) {
		super(convert, dealer, channelBO);
		this.orderService = orderService;
		String name = "彩种" + channelBO.getLotteryCode() + "，渠道商主键ID：" + channelBO.getId() + "，出票渠道线程";
		// 每个出票渠道配置启动一个送票线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						sendTicket();
					} catch (Exception e) {
						LOGGER.info("送票线程异常：" + name, e);
					}
					try {
						Thread.sleep(1000 * 60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}, name).start();
	}

	@Override
	public boolean addTicket(TicketBO bo) {
		// 进行票类容转换
		boolean success = convert.handle(bo);
		if (success) {
			ticketQueue.add(bo);
		} else {
			bo.setTicketRemark("票格式转换错误");
		}
		if (ticketQueue.size() > 1000) {
			LOGGER.info("送票列队超1000（" + ticketQueue.size() + "），彩种" + channelBO.getLotteryCode() + "，渠道商主键ID："
					+ channelBO.getId());
		}
		return success;
	}

	/**
	 * @see 从列队中获取票，当票满足打包条件或者到了最后出票时间，将调用出票商接口出票
	 */
	@Override
	protected void sendTicket() {
		long endOutTime = 0;
		for (;;) {
			TicketBO ticketBO = null;
			try {
				// 当不存在票是永久阻塞，有票是根据最晚出票时间进行延迟阻塞
				if (endOutTime == 0) {
					ticketBO = ticketQueue.take();
				} else {
					long delayTime = endOutTime - System.currentTimeMillis();
					ticketBO = ticketQueue.poll(delayTime, TimeUnit.MILLISECONDS);
				}
			} catch (InterruptedException e) {
				LOGGER.error("从阻塞离队中获取票失败", e);
			}
			// 如果到达延迟送票时间没有取出票，就进行送票
			if (ticketBO == null) {
				endOutTime = 0;
			} else {
				// 计算票需要延迟送票时间
				long outTime = ticketBO.getOutTime().getTime();
				if (endOutTime < System.currentTimeMillis() || endOutTime > outTime) {
					endOutTime = outTime;
				}
				addNotSendTicketMap(ticketBO);
			}
			// 根据彩种code进行打包
			// 判断是否需要永久阻塞，如果存在没有送的票延迟阻塞
			boolean isClear = true;
			for (Set<TicketBO> set : notSendTicketMap.values()) {
				if (set.size() == 0) {
					continue;
				}
				// 送票条件
				if (endOutTime == 0 || set.size() >= sendEachBatch) {
					List<TicketBO> list = new ArrayList<>();
					list.addAll(set);
					set.clear();
					doSendTicket(list);
				}
				if (set.size() > 0) {
					isClear = false;
				}
			}
			if (isClear) {
				endOutTime = 0;
			}
		}
	}

	/**
	 * 送票处理
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月8日 下午4:43:24
	 * @param list
	 */
	private void doSendTicket(final List<TicketBO> list) {
		pool.submit(new Runnable() {
			@Override
			public void run() {
				checkTicketStatus(list);
				if (list.isEmpty()) {
					return;
				}
				Set<String> orders = new HashSet<>();
				for (TicketBO ticketBO : list) {
					orders.add(ticketBO.getOrderCode());
				}
				try {
					// 修改订单为出票中
					orderService.updateOrderStatus(orders, OrderStatus.TICKETING.getValue());
					boolean success = dealer.sendTicket(list);
					if (success) {
						Set<String> set = new HashSet<>();
						for (TicketBO ticketBO : list) {
							String batchNum = ticketBO.getBatchNum();
							if (set.contains(batchNum) || ticketBO.getTicketStatus() != TicketStatus.SEND.getValue()) {
								continue;
							}
							CheckBO bo = new CheckBO(ticketBO.getLotteryCode(), ticketBO.getLotteryChildCode(),
									ticketBO.getLotteryIssue(), batchNum, dealer, ticketBO.getEndTicketTime());
							dealer.addCheckBatchNum(bo);
							set.add(batchNum);
						}
					}
				} catch (Exception e) {
					LOGGER.info("送票异常", e);
					String message = e.getMessage();
					if (message.length() > 80) {
						message = "送票失败,服务器异常:" + message.substring(0, 80);
					}
					for (TicketBO ticketBO : list) {
						ticketBO.setTicketStatus(TicketStatus.SEND_FAIL.getValue());
						ticketBO.setTicketRemark(message);
					}
				}
				orderService.updateTicketSendDealer(list);
			}
		});
	}

	@Override
	public List<TicketBO> clearChannelGet() {
		List<TicketBO> list = new ArrayList<>();
		list.addAll(ticketQueue);
		ticketQueue.clear();
		for (Set<TicketBO> ticketBO : notSendTicketMap.values()) {
			list.addAll(ticketBO);
		}
		notSendTicketMap.clear();
		return list;
	}

	@Override
	public int getSendTicket() {
		return orderService.getSendTicket(channelBO.getLotteryCode(), channelBO.getTicketChannelId());
	}

	/**
	 * 添加票信息到即将送票map
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月23日 下午3:25:02
	 * @param ticketBO
	 */
	private void addNotSendTicketMap(TicketBO ticketBO) {
		String key = sendTicketKey(ticketBO);
		Set<TicketBO> set = notSendTicketMap.get(key);
		if (set == null) {
			set = new HashSet<>();
			notSendTicketMap.put(key, set);
		}
		set.add(ticketBO);
	}

	/**
	 * 送票前对票状态检查 检查票票状态是否是已分配状态，如果不是移除该票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月3日 下午3:41:18
	 * @param list
	 */
	private void checkTicketStatus(final List<TicketBO> list) {
		List<Integer> ids = new ArrayList<>();
		for (TicketBO ticketBO : list) {
			ids.add(ticketBO.getId());
		}
		// 检查票状态是否是已分配状态，如果不是移除该票
		List<Integer> effectiveIds = orderService.validTicket(ids);
		if (ids.size() != effectiveIds.size()) {
			Iterator<TicketBO> it = list.iterator();
			while (it.hasNext()) {
				TicketBO bo = it.next();
				if (!effectiveIds.contains(bo.getId())) {
					it.remove();
				}
			}
		}
	}
}
