package com.hhly.ticket.service.ticket.distribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.hhly.ticket.base.common.OrderEnum.OrderStatus;
import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.controller.ChannelController;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.OrderTicketInfoBO;
import com.hhly.ticket.service.entity.SendTime;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.AbstractTicketChannel;
import com.hhly.ticket.service.ticket.IOrderDistribute;
import com.hhly.ticket.service.ticket.ISource;
import com.hhly.ticket.service.ticket.ITicketChannel;
import com.hhly.ticket.service.ticket.TicketHandler;
import com.hhly.ticket.util.CollectionUtil;

/**
 * @desc 订单票进行出票商分配
 * @author jiangwei
 * @date 2017年8月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OrderDistribute implements IOrderDistribute {

	private static final Logger LOGGER = Logger.getLogger(ChannelController.class);

	// 默认送票间隔
	private int[] defualtSendLaw = { 300, 1200, 10 };
	// 每次最多处理900张票
	private int maxTicket = 900;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private TicketHandler handler;
	@Autowired
	private CountSource countSource;
	@Autowired
	private MoneySource moneySource;
	
	private Random random = new Random();

	@Override
	public void distribute(List<TicketBO> tickets) {

		Map<Integer, List<TicketBO>> mapTikcet = new HashMap<>();
		// 按彩种把票分类
		for (TicketBO ticketBO : tickets) {
			if (!mapTikcet.containsKey(ticketBO.getLotteryCode())) {
				mapTikcet.put(ticketBO.getLotteryCode(), new ArrayList<TicketBO>());
			}
			mapTikcet.get(ticketBO.getLotteryCode()).add(ticketBO);
		}
		for (Map.Entry<Integer, List<TicketBO>> entry : mapTikcet.entrySet()) {
			// 进行权重分配
			distribute(entry.getValue(), entry.getKey(),null);
		}
	}

	/**
	 * @see 彩种可能存在多个出票商，将采用不同的策略
	 */
	@Override
	public void distribute(List<TicketBO> ticket, int lotteryCode,String channelId) {
		// 所有出票商
		List<ITicketChannel> channels = handler.getChannel(lotteryCode);
		if (CollectionUtils.isEmpty(channels)) {
			throw new ServiceRuntimeException("送票错误,不存在出票商");
		}
		ITicketChannel appointChannel = null;
		//指定出票商
		if(StringUtils.isNotBlank(channelId)){
			for (ITicketChannel iTicketChannel : channels) {
				if(channelId.equals(iTicketChannel.getChannelId())){
					appointChannel = iTicketChannel;
					break;
				}
			}
			if(appointChannel == null){
				throw new ServiceRuntimeException("送票错误,不存在有效的指定出票商");
			}
		}
		//只存在一个出票商
		if(appointChannel == null && channels.size() == 1){
			 appointChannel = channels.get(0);
		}
		// 单次最多处理900张票
		List<List<TicketBO>> allList = CollectionUtil.subList(ticket, maxTicket);
		List<TicketBO> error = new ArrayList<>();
		for (List<TicketBO> list : allList) {
			if (appointChannel != null) {
				// 只存在一个出票商
				// 检查子玩法，
				error.addAll(checkLotteryChildCode(appointChannel.getStopLottery(), list));
				error.addAll(distributeByOne(appointChannel, list));
			} else {
				error = distributeByMany(channels, list);
			}
		}
		handlerNotOutTicket(error, lotteryCode);
	}

	/**
	 * 多个出票商进行出票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月3日 下午4:32:10
	 * @param channels
	 * @param list
	 * @return
	 */
	private List<TicketBO> distributeByMany(List<ITicketChannel> channels, List<TicketBO> list) {
		// 按照订单进行筛选
		Map<String, OrderTicketInfoBO> orderInfo = new HashMap<>();
		for (TicketBO ticketBO : list) {
			OrderTicketInfoBO info = orderInfo.get(ticketBO.getOrderCode());
			if (info == null) {
				info = new OrderTicketInfoBO(channels);
				orderInfo.put(ticketBO.getOrderCode(), info);
				//判断渠道是否积分兑换码渠道
				for (ITicketChannel channel : channels) {
					if(StringUtils.isBlank(ticketBO.getRedeemCode())){
						if(channel.isIntegral()){
							info.romoveSendChannel(channel);
							info.setMessage("渠道只能积分送票");
						}
					}else{
						if(!ticketBO.getChannelId().equals(channel.getChannelId())){
							info.romoveSendChannel(channel);
							info.setMessage("渠道不能积分送票");
						}
					}
				}
			}
			info.addTicket(ticketBO);
		}
		// 判断订单是否有子玩法存在出票商不能送票,玩法
		for (ITicketChannel channel : channels) {
			//玩法
			for (TicketBO bo : list) {
				if(!channel.isPassMode(bo)){
					orderInfo.get(bo.getOrderCode()).romoveSendChannel(channel);
					orderInfo.get(bo.getOrderCode()).setMessage("渠道不支持该投注方式");
				}
			}
			List<String> stopLottery = channel.getStopLottery();
			//子玩法
			if (CollectionUtils.isEmpty(stopLottery)) {
				continue;
			}
			for (TicketBO bo : list) {
				if (stopLottery.contains("" + bo.getLotteryChildCode())) {
					orderInfo.get(bo.getOrderCode()).romoveSendChannel(channel);
					orderInfo.get(bo.getOrderCode()).setMessage("渠道停售该玩法");
				}
			}
		}
		List<TicketBO> error = new ArrayList<>();
		for (OrderTicketInfoBO bo : orderInfo.values()) {
			error.addAll(manyChannel(bo));
		}
		return error;
	}

	/**
	 * 对单个订单进行多个出票商处理
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月3日 下午4:32:50
	 * @param channels
	 * @param tickets
	 * @return
	 */
	private Collection<? extends TicketBO> manyChannel(OrderTicketInfoBO bo) {
		// 如果不存在出票商能出票
		if (CollectionUtils.isEmpty(bo.getChannels())) {
			for (TicketBO ticketBO : bo.getTickets()) {
				ticketBO.setTicketRemark(bo.getMessage());
			}
			return bo.getTickets();
		}
		return doManyChannel(bo.getChannels(), bo.getTickets());
	}

	/**
	 * 多个出牌商送票进行分配
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月3日 下午4:35:12
	 * @param channels
	 * @param tickets
	 * @return
	 */
	private Collection<? extends TicketBO> doManyChannel(List<ITicketChannel> channels, List<TicketBO> tickets) {
		TicketBO first = tickets.get(0);
		double max = first.getTicketMoney();
		double min = first.getTicketMoney();
		for (TicketBO ticketBO : tickets) {
			double temp = ticketBO.getTicketMoney();
			if(max < temp){
				max = temp;
			}else if(min > temp){
				min = temp;
			}
		}
		ISource source = getSource(first.getLotteryCode(), first.getEndTicketTime());
		ITicketChannel sendChannel = leastSendTicketChannel(channels, source, min, max);
		List<TicketBO> error = distributeByOne(sendChannel, tickets);
		if (error.isEmpty()) {
			source.add(sendChannel, tickets);
		} else {
			channels.remove(sendChannel);
			// 分配渠道送票存在失败，重新送票到其他出票商
			for (ITicketChannel channel : channels) {
				List<TicketBO> temp = error;
				error = distributeByOne(channel, temp);
				if (error.isEmpty()) {
					source.add(channel, temp);
					break;
				}
			}
		}
		return error;
	}
    /**
     * 计算列表中最少送票的渠道
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年9月7日 上午11:47:34
     * @param channels 渠道集合
     * @param source 计算资源类
     * @param max 票最大金额
     * @param min 票最小金额
     * @param tickets 
     * @return
     */
	private ITicketChannel leastSendTicketChannel(List<ITicketChannel> channels, ISource source, double min, double max) {
		ITicketChannel sendChannel = null;
		int diff = 0;
		for (int i = 0; i < channels.size(); i++) {
			ITicketChannel channel = channels.get(i);
			if(!channel.isContain(min, max)){
				continue;
			}
			if(sendChannel == null){
				sendChannel = channel;
				continue;
			}
			int current = channel.getDifference(sendChannel, source);
			if (current < diff) {
				diff = current;
				sendChannel = channel;
			}
		}
		if(sendChannel == null){
			sendChannel = channels.get(random.nextInt(channels.size()));
		}
		return sendChannel;
	}

	/**
	 * 根据出票截止时间获取送票元数据获取算法 高频彩只能按照金额分配
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月3日 下午4:39:32
	 * @param date
	 * @return
	 */
	private ISource getSource(int lotteryCode, Date date) {
		ISource source = null;
		if (lotteryCode >= 200 && lotteryCode < 300) {
			source = moneySource;
		} else {
			long time = date.getTime() - System.currentTimeMillis();
			if (time > AbstractTicketChannel.getExcisionTime()) {
				source = moneySource;
			} else {
				source = countSource;
			}
		}
		return source;
	}

	/**
	 * 处理不能送票的票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月2日 下午4:56:23
	 * @param error
	 */
	private void handlerNotOutTicket(List<TicketBO> error, int lotteryCode) {
		if (CollectionUtils.isEmpty(error)) {
			return;
		}
		Set<String> orderCode = new HashSet<>();
		int status = TicketStatus.SEND_FAIL.getValue();
		Iterator<TicketBO> iter = error.iterator();
		Map<String, String> changeFail = new HashMap<>();
		while (iter.hasNext()) {
			TicketBO ticketBO = iter.next();
			//票为已送票
			if (TicketStatus.SEND.getValue() == ticketBO.getTicketStatus().intValue()) {
				changeFail.put(String.valueOf(ticketBO.getId()), ticketBO.getTicketRemark());
				LOGGER.info("订单：" + ticketBO.getOrderCode() + "票：" + ticketBO.getId() + ":切换出票商失败！");
				iter.remove();
				continue;
			}
			if (ticketBO.getChangeType() != 1) {
				orderCode.add(ticketBO.getOrderCode());
			}
			ticketBO.setTicketStatus(status);
			LOGGER.info(
					"订单：" + ticketBO.getOrderCode() + "票：" + ticketBO.getId() + "，出票失败：" + ticketBO.getTicketRemark());
		}
		orderService.updateOrderStatus(orderCode, OrderStatus.FAILING_TICKET.getValue());
		orderService.updateTicketStatusError(error);
		if (!changeFail.isEmpty()) {
			throw new ServiceRuntimeException("票id：" + JSONUtils.toJSONString(changeFail) + "切票失败");
		}
	}

	/**
	 * 处理一个出票商
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月6日 下午3:23:15
	 * @param channel
	 * @param ticket
	 */
	private List<TicketBO> distributeByOne(ITicketChannel channel, List<TicketBO> ticket) {
		List<TicketBO> error = new ArrayList<>();
		Set<String> errorCode = new HashSet<>();
		if (CollectionUtils.isEmpty(ticket)) {
			return error;
		}
		// 送票渠道ID
		List<TicketBO> effectiveList = new ArrayList<>();
		String channelId = channel.getChannelId();
		// 送票
		next: for (int i = 0; i < ticket.size(); i++) {
			TicketBO ticketBO = ticket.get(i);
			//玩法
			if(!channel.isPassMode(ticketBO)){
				ticketBO.setTicketRemark("该出票商不支持该玩法");
				error.add(ticketBO);
				errorCode.add(ticketBO.getOrderCode());
				continue next;
			}
			//判断票是否是积分兑换
			if(StringUtils.isNotBlank(ticketBO.getRedeemCode())){
				if(!channelId.equals(ticketBO.getChannelId())){
					ticketBO.setTicketRemark("该渠道不能积分出票");
					error.add(ticketBO);
					errorCode.add(ticketBO.getOrderCode());
					continue next;
				}
			}
			ticketBO.setChannelId(channelId);
			TicketBO before = null;
			if(i > 0){
				before = ticket.get(i-1);
			}
			handleTicketOutTime(channel, ticketBO, before);
			if (ticketBO.isEffectiveSendTime()) {
				ticketBO.setBeforeTicketStatus(ticketBO.getTicketStatus());
				ticketBO.setTicketStatus(TicketStatus.ALLOCATION.getValue());
				ticketBO.setTicketRemark("已分配出票商");
				effectiveList.add(ticketBO);
			}else{
				ticketBO.setTicketRemark("不能再有效送票时间或订单其它票存在不能送票情况");
				errorCode.add(ticketBO.getOrderCode());
				error.add(ticketBO);
			}
		}
		Iterator<TicketBO> iterator = effectiveList.iterator();
		while (iterator.hasNext()) {
			TicketBO bo = iterator.next();
			if(errorCode.contains(bo.getOrderCode())){
				bo.setTicketStatus(bo.getBeforeTicketStatus());
				bo.setTicketRemark("订单其它票存在不能送票情况");
				error.add(bo);
				iterator.remove();
				continue;
			}
			orderService.updateChangeChannel(bo);
		}
		//修改原有票
		orderService.updateTicketAllocation(effectiveList);
		for (TicketBO ticketBO : effectiveList) {
			if (!channel.addTicket(ticketBO)) {
				error.add(ticketBO);
			}
		}
		return error;
	}
	/**
	 * 处理票的出票时间
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月9日 下午6:07:38
	 * @param channel
	 * @param ticketBO
	 * @param before
	 */
	private void handleTicketOutTime(ITicketChannel channel, TicketBO current, TicketBO before) {
		//计算出票商有效送票时间，如果当前票与前一张判断条件成立就省略计算
		if (before != null && before.getSaleTime().getTime() == current.getSaleTime().getTime()
				&& before.getEndTicketTime().getTime() == current.getEndTicketTime().getTime()
				&& before.getLotteryCode() == current.getLotteryCode()) {
			current.setSaleTime(before.getSaleTime());
			current.setOutTime(before.getOutTime());
			current.setEffectiveSendTime(before.isEffectiveSendTime());
		} else {
			calculateOutTime(channel, current);
		}
	}

	/**
	 * 检查子玩法是否合法，是否停止送票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月6日 下午5:01:52
	 * @param stopLottery
	 *            停送子玩法
	 * @param ticket
	 *            需要出票的票
	 * @return 不能送票的票信息
	 */
	private List<TicketBO> checkLotteryChildCode(List<String> stopLottery, List<TicketBO> ticket) {
		List<TicketBO> error = new ArrayList<>();
		if (!CollectionUtils.isEmpty(stopLottery)) {
			Set<String> orderCode = new HashSet<>();
			for (TicketBO ticketBO : ticket) {
				if (stopLottery.contains("" + ticketBO.getLotteryChildCode())) {
					orderCode.add(ticketBO.getOrderCode());
				}
			}
			if (orderCode.size() > 0) {
				Iterator<TicketBO> it = ticket.iterator();
				while (it.hasNext()) {
					TicketBO bo = it.next();
					if (orderCode.contains(bo.getOrderCode())) {
						it.remove();
						bo.setTicketRemark("渠道停送子玩法");
						error.add(bo);
					}
				}
			}
		}
		return error;
	}

	/**
	 * 计算票最晚送票时间 根据配置信息比如（如果场次截止前20分钟5分钟送一次，20分钟后10秒一次）
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月6日 下午6:08:34
	 * @param lotteryCode
	 *            彩种
	 * @param endTicketTime
	 *            最晚截止出票时间
	 * @return 是否存在出票有效时间
	 */
	private void calculateOutTime(ITicketChannel channel, TicketBO ticketBO) {
		SendTime sendTime = getTheoreticsSendTime(channel, ticketBO);
		// 有效送票时间
		long effectiveTime = getEffectiveTime(channel, ticketBO.getEndTicketTime().getTime(), sendTime);
		if (effectiveTime == -1) {	
			ticketBO.setEffectiveSendTime(false);
		} else {
			Date date = new Date(effectiveTime);
			ticketBO.setOutTime(date);
			// 如果计算出来理论最晚送票时间和最终有效送票时间不相等，说明出票商在最终送票时间前不能送票
			// 不能提前打包送票（到时间才能从列队中取出来）
			if (sendTime.getOutTime() != effectiveTime) {
				ticketBO.setSaleTime(date);
			}
			ticketBO.setEffectiveSendTime(true);
		}
	}

	/**
	 * 计算理论送票时间和送票间隔
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月6日 下午6:14:57
	 * @param channel
	 *            渠道
	 * @param ticketBO
	 *            票
	 * @return
	 */
	private SendTime getTheoreticsSendTime(ITicketChannel channel, TicketBO ticketBO) {
		long saleTime = ticketBO.getSaleTime().getTime();
		// 送票间隔
		int interval = 0;
		long outTime = 0;
		if (System.currentTimeMillis() < saleTime) {
			outTime = saleTime;
		} else {
			interval = getInterval(channel.getSendLaw(), ticketBO.getEndTicketTime().getTime());
			// 计算理论送票时间
			outTime = calculateIntervalTime(interval, channel.getRandom());
		}
		return new SendTime(interval, outTime);
	}

	/**
	 * 获取有效的送票时间
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月6日 下午5:51:13
	 * @param channel
	 *            渠道商
	 * @param endTicketTime
	 *            最晚出票时间
	 * @param sendTime
	 *            理论送票时间
	 * @return 不存在有效时间返回-1，存在返回时间戳
	 */
	private long getEffectiveTime(ITicketChannel channel, long endTicketTime, SendTime sendTime) {
		endTicketTime = endTicketTime + channel.getDealerEndTime();
		Date date = new Date(sendTime.getOutTime());
		long effective = -1;
		// 依次每天进行有效时间计算判断
		for (int i=0;;i++) {
			if (date.getTime() < endTicketTime) {
				effective = channel.checkOutTime(date.getTime(), sendTime.getInterval());
				if (effective != -1 && effective < endTicketTime) {
					break;
				}
			} else {
				break;
			}
			//第二天从0点可以检测有效时间
			if(i==0){
				date = DateUtils.setHours(date, 0);
				date = DateUtils.setMinutes(date, 0);
				date = DateUtils.setSeconds(date, 0);
			}
			date = DateUtils.addDays(date, 1);
		}
		return effective;
	}
	/**
	 * 获取送票间隔 如果不存在使用默认配置
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月6日 下午4:12:53
	 * @param time
	 *            配置送票间隔
	 * @param endTicketTime
	 *            最终送票时间
	 * @return
	 */
	private int getInterval(int[] time, long endTicketTime) {
		if (time == null) {
			time = defualtSendLaw;
		}
		// 计算是否到了送票分割时间段
		long num = (endTicketTime - System.currentTimeMillis()) / 1000 - time[1];
		if (num > 0) {
			return time[0];
		} else {
			return time[2];
		}
	}

	/**
	 * 根据随机数，间隔，计算出下一个有效的时间（毫秒）
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月2日 下午4:01:42
	 * @param interval
	 *            时间间隔（比如每隔10秒是一个有效的时间间隔）
	 * @param random
	 *            一个随机数，使相同间隔的产生不同的有效时间（使时间能够平局分配）
	 * @return
	 */
	private long calculateIntervalTime(int interval, int random) {
		long outTime = System.currentTimeMillis() / 1000;
		long cal = outTime - outTime % interval + random % interval;
		cal = cal < outTime ? cal + interval : cal;
		return cal * 1000 + random % 1000;
	}
}
