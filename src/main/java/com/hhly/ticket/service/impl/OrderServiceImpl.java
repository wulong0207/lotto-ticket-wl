package com.hhly.ticket.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.ticket.base.common.OrderEnum.OrderStatus;
import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.base.thread.ThreadPoolManager;
import com.hhly.ticket.base.vo.AgainOutTicketVO;
import com.hhly.ticket.persistence.dao.OrderInfoDaoMapper;
import com.hhly.ticket.persistence.dao.TicketInfoDaoMapper;
import com.hhly.ticket.persistence.dao.TimeTaskSingleDaoMapper;
import com.hhly.ticket.service.IChannelManageService;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.OrderStatusBO;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.entity.TicketStatusBO;
import com.hhly.ticket.service.ticket.IOrderDistribute;
import com.hhly.ticket.service.ticket.ITicketChannel;
import com.hhly.ticket.service.ticket.SendMessage;
import com.hhly.ticket.service.ticket.TicketHandler;
import com.hhly.ticket.util.TicketUtil;

@Service
public class OrderServiceImpl implements IOrderService {

	private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

	@Autowired
	private TicketInfoDaoMapper ticketInfoDaoMapper;

	@Autowired
	private OrderInfoDaoMapper orderInfoDaoMapper;
	
	@Autowired
	private TimeTaskSingleDaoMapper timeTaskSingleDaoMapper;

	@Autowired
	private IChannelManageService channelManageService;

	@Autowired
	private IOrderDistribute distribute;

	@Autowired
	private SendMessage sendMessage;

	@Autowired
	private TicketHandler handler;

	@Override
	public List<Integer> validTicket(List<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<>();
		}
		return ticketInfoDaoMapper.listTicketStatus(ids, TicketStatus.ALLOCATION.getValue());
	}

	@Override
	public List<TicketBO> getTicket(List<String> orders) {
		List<TicketBO> result = ticketInfoDaoMapper.getTicket(orders);
		return result;
	}

	@Override
	public void updateOrderStatus(Set<String> orders, int status) {
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}
		orderInfoDaoMapper.updateOrderStatus(orders, status);
		sendOrderStatusMessage(orders, status);
	}

	@Override
	public void updateTicketStatusByOrderCode(Collection<String> orderCodes, int status, String reamk) {
		if (CollectionUtils.isEmpty(orderCodes)) {
			return;
		}
		ticketInfoDaoMapper.updateTicketStatusByOrderCode(orderCodes, status, reamk);
		List<TicketBO> list = ticketInfoDaoMapper.listTicketInfoFail(orderCodes, null);
		ticketLog(list);
	}

	@Override
	public void updateTicketSendDealer(List<TicketBO> list) {
		ticketInfoDaoMapper.updateTicketSendDealer(list);
		ticketLog(list);
	}

	/**
	 * 打印日志
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月8日 下午3:34:26
	 * @param list
	 */
	private void ticketLog(final List<TicketBO> list) {
		ThreadPoolManager.schedule(new Runnable() {
			@Override
			public void run() {
				List<TicketBO> fial = new ArrayList<>();
				for (TicketBO ticketBO : list) {
					Integer status = ticketBO.getTicketStatus();
					if (status == null) {
						continue;
					}
					if (status == -1) {
						LOGGER.info("送票失败：" + ticketBO);
						fial.add(ticketBO);
					} else if (status == -2) {
						LOGGER.info("出票失败：" + ticketBO);
						fial.add(ticketBO);
					}
				}
				try {
					sendMessage.sendTicketFail(fial);
				} catch (Exception e) {
					LOGGER.info("", e);
				}
			}
		}, 1, TimeUnit.SECONDS);
	}

	@Override
	public void updateTicketAllocation(List<TicketBO> effectiveList) {
		if (CollectionUtils.isEmpty(effectiveList)) {
			return;
		}
		ticketInfoDaoMapper.updateTicketAllocation(effectiveList);
	}

	@Override
	public List<TicketBO> listAllocationTicket(Date outTime) {
		return ticketInfoDaoMapper.listAllocationTicket(outTime);
	}

	@Override
	public Collection<String> updateOutTicket(List<TicketBO> tickets) {
		Set<String> batchNums = new HashSet<>();
		Set<String> ticketChanngeBatch = new HashSet<>();
		for (TicketBO ticketBO : tickets) {
			batchNums.add(ticketBO.getBatchNum());
			if(ticketBO.getTicketStatus().intValue() == TicketStatus.OUT_TICKET.getValue()){
				ticketChanngeBatch.add(ticketBO.getBatchNum());
			}
		}
		if (tickets.size() == 1) {
			int num = ticketInfoDaoMapper.countTicketStatus(tickets.get(0));
			if (num == 1) {
				LOGGER.info("重复修改票状态过滤：" + tickets.get(0));
				return batchNums;
			}
		}
		ticketInfoDaoMapper.updateOutTicket(tickets);
		ticketLog(tickets);
		updateOrderStatusByBetch(batchNums);
		return batchNums;
	}
	
	@Override
	public void updateOrderStatusByBetch(Collection<String> betchNum) {
		LOGGER.info("更新批次号订单状态：" + betchNum);
		List<OrderStatusBO> orders = new ArrayList<>();
		// 查询批次号订单所有票状态
		List<TicketStatusBO> statusBOs = ticketInfoDaoMapper.listOrderTicketStatus(betchNum);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("查询订单状态：" + statusBOs);
		}
		ticket: for (TicketStatusBO bo : statusBOs) {
			OrderStatusBO statusBO = new OrderStatusBO();
			statusBO.setOrderCode(bo.getOrderCode());
			statusBO.setOrderStatus(6);// 已出票
			String[] str = bo.getStatus().split(SymbolConstants.COMMA);
			// -2出票失败;-1送票失败;0不出票;1待分配;2已分配;3已送票;4已出票
			boolean isUpdate = true;
			// 判断票状态，从而更新订单状态
			status: for (int i = 0; i < str.length; i++) {
				switch (str[i]) {
				case "4":
					break;
				case "3":
				case "2":
				case "1":
					isUpdate = false;
					break;
				case "0":
					continue ticket;
				case "-1":
				case "-2":
					// 出票失败，不同步订单状态为出票失败，最终检票时候再同步
					isUpdate = false;
					statusBO.setOrderStatus(7);// 出票失败
					break status;
				default:
					break;
				}
			}
			if (isUpdate) {
				orders.add(statusBO);
			}
		}
		if (orders.isEmpty()) {
			return;
		}
		updateOrderStatusOutTicket(orders);
		LOGGER.info("更新订单出票状态：" + orders.toString());
	}

	@Override
	public void updateOrderStatusOutTicket(List<OrderStatusBO> orders) {
		Date now = new Date();
		for (OrderStatusBO orderStatusBO : orders) {
			if (orderStatusBO.getOrderStatus() == OrderStatus.TICKETED.getValue()) {
				orderStatusBO.setComeOutTime(now);
			}else if (orderStatusBO.getOrderStatus() == OrderStatus.FAILING_TICKET.getValue()) {
				orderStatusBO.setComeOutTime(null);
			}
			int num = orderInfoDaoMapper.updateOrderStatusOutTicketByOne(orderStatusBO);
			if(num == 1 ){
				sendOrderStatusMessage(orderStatusBO.getOrderCode(), orderStatusBO.getOrderStatus());
			}else{
				LOGGER.info("订单状态已修改："+orderStatusBO);
			}
		}
	}
	
	/**
	 * 发送订单通知信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年6月19日 下午5:02:02
	 * @param orderCode
	 * @param orderStatus
	 */
	private void sendOrderStatusMessage(String orderCode, int orderStatus) {
		List<String> order = new ArrayList<>();
		order.add(orderCode);
		sendOrderStatusMessage(order, orderStatus);
	}

	/**
	 * 发送票状态（不同的订单状态进行不同类型的通知）
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月13日 下午2:24:50
	 * @param orders
	 *            订单
	 * @param status
	 *            状态
	 */
	private void sendOrderStatusMessage(final Collection<String> orders, final int status) {
		ThreadPoolManager.schedule(new Runnable() {
			public void run() {
				if (OrderStatus.TICKETING.getValue() == status) {
					sendMessage.sendOuting(orders);
				} else if (OrderStatus.TICKETED.getValue() == status) {
					sendMessage.sendOutSuccess(orders);
				} else if (OrderStatus.FAILING_TICKET.getValue() == status) {
					if (CollectionUtils.isNotEmpty(orders)) {	
						sendMessage.sendOutFail(orders);
						LOGGER.info("订单出票失败：" + orders);
					}
				}
			}
		}, 5, TimeUnit.SECONDS);
	}

	@Override
	public List<DealerCheckBO> listBatchNumCheckTicket(Integer lotteryCode, String batchNum, String orderCode) {
		List<String> batchNums = null;
		if (StringUtils.isNotBlank(batchNum)) {
			batchNums = Arrays.asList(batchNum.split(SymbolConstants.COMMA));
		}
		return ticketInfoDaoMapper.listBatchNumCheckTicket(lotteryCode, batchNums, orderCode);
	}

	@Override
	public void againOutTikcet(AgainOutTicketVO againOutTicketVO) {
		if (StringUtils.isNotBlank(againOutTicketVO.getTicket())) {
			List<String> ticketIds = Arrays.asList(againOutTicketVO.getTicket().split(SymbolConstants.COMMA));
			againOutTicketVO.setTicketIds(ticketIds);
		}
		checkSendTicketInfo(againOutTicketVO);
		List<TicketBO> tickets = ticketInfoDaoMapper.getAgainTicket(againOutTicketVO);
		List<TicketBO> out = new ArrayList<>();
		for (TicketBO ticketBO : tickets) {
			//拼接切票记录
			ticketBO.setChangeType(againOutTicketVO.getType());
			if(againOutTicketVO.getType() == 1){
				if(StringUtils.isNotBlank(ticketBO.getBatchNum())){
					String change = ticketBO.getTicketChange();
					if(StringUtils.isBlank(change)){
						change  ="";
					}else{
						change +=";";
					}
					change = change + ticketBO.getChannelId() + "#" + ticketBO.getBatchNum();
					ticketBO.setTicketChange(change);
				}
			}
			if (ticketBO.getTicketStatus() == TicketStatus.NO_ALLOCATION.getValue()
					|| ticketBO.getTicketStatus() == TicketStatus.SEND_FAIL.getValue()
					|| ticketBO.getTicketStatus() == TicketStatus.ERROR.getValue()
					|| ticketBO.getTicketStatus() == TicketStatus.ALLOCATION.getValue()) {
				// 能重新送票订单
				out.add(ticketBO);
			}else if (ticketBO.getTicketStatus() == TicketStatus.SEND.getValue()){
				if(againOutTicketVO.getType() == 1){
					out.add(ticketBO);
				}
			}
		}
		if (!out.isEmpty()) {
			distribute.distribute(out, out.get(0).getLotteryCode(),againOutTicketVO.getChannelId());
		}
	}
	/**
	 * 对送票失败的票进行检票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月19日 下午4:39:01
	 * @param againOutTicketVO
	 */
	private void checkSendTicketInfo(AgainOutTicketVO againOutTicketVO) {
		List<DealerCheckBO> batchNums = ticketInfoDaoMapper.getTicketInfoSendFail(againOutTicketVO);
		if (!batchNums.isEmpty()) {
			List<String> outing = channelCheck(batchNums);
			if(CollectionUtils.isNotEmpty(outing)){
				ticketInfoDaoMapper.updateSendTicketAbnormal(outing);	
			}
		}
	}

	@Override
	public void updateOutTicketByBatchNum(List<String> batchNum, int status, String remark) {
		ticketInfoDaoMapper.updateTicketByBatchNum(batchNum, status, remark);
		updateOrderStatusByBetch(batchNum);
		List<TicketBO> list = ticketInfoDaoMapper.listTicketInfoFail(null, batchNum);
		ticketLog(list);
	}

	@Override
	public void updateOrderOutFial(Integer lotteryCode) {
		List<String> list = orderInfoDaoMapper.listOrderOuting(lotteryCode);
		if (list.isEmpty()) {
			return;
		}
		LOGGER.info("最终检票时状态出票中订单：" + list);
		// 检查订单的票是否全部出票完成
		List<String> fialOrder = orderInfoDaoMapper.listOrderOutFail(list);
		List<OrderStatusBO> orders = new ArrayList<>();
		for (String order : list) {
			OrderStatusBO bo = new OrderStatusBO();
			bo.setOrderCode(order);
			if (fialOrder.contains(order)) {
				bo.setOrderStatus(7);// 出票失败
			} else {
				bo.setOrderStatus(6);// 已出票
			}
			orders.add(bo);
		}
		LOGGER.info("最终检票-同步订单状态：" + orders);
		updateOrderStatusOutTicket(orders);
	}

	@Override
	public void allocation(String type, String lotteryCode, String lotteryIssue) {
		if ("1".equals(type)) {
			// 建议执行时间表达式 "3 2/10 * * * ?" ,待分配（拆票后10分钟未分配的票）
			Date splitTime = DateUtils.addMinutes(new Date(), -10);
			List<TicketBO> tickets = ticketInfoDaoMapper.listNoAllocationTicket(splitTime, null, null);
			if (!tickets.isEmpty()) {
				LOGGER.info("拆票后未及时分配" + tickets.toString());
				distribute.distribute(tickets);
			}
		} else if ("2".equals(type)) {
			// 已分配，超过分配时间一个小时未送票的票
			// 建议执行时间表达式 "20 0 0/1 * * ?"
			Date outTime = DateUtils.addMinutes(new Date(), -60);
			List<TicketBO> tickets = ticketInfoDaoMapper.listAllocationTicket(outTime);
			if (!tickets.isEmpty()) {
				LOGGER.info("未及时送票订单" + tickets.toString());
				distribute.distribute(tickets);
			}
		} else if ("3".equals(type)) {
			// 待分配，超过30秒未分配的票
			Date splitTime = DateUtils.addSeconds(new Date(), -30);
			List<TicketBO> tickets = ticketInfoDaoMapper.listNoAllocationTicket(splitTime, lotteryCode, lotteryIssue);
			if (!tickets.isEmpty()) {
				LOGGER.info("超过30秒 拆票后未及时分配" + tickets.toString());
				distribute.distribute(tickets);
			}
		} else {
			throw new ServiceRuntimeException("对未及时送票进行处理不存在该类型");
		}
	}

	@Override
	public void updateTicketStatusError(List<TicketBO> error) {
		if(CollectionUtils.isEmpty(error)){
			return;
		}
		ticketLog(error);
		ticketInfoDaoMapper.updateTicketStatusError(error);

	}

	@Override
	public void updateBalance() {
		Map<String, Double> balances = TicketHandler.getChannelBalance();
		for (Map.Entry<String, Double> entry : balances.entrySet()) {
			double balance = entry.getValue();
			String channelId = entry.getKey();
			if (balance != -1 && balance < 10000) {
				ChannelBO channelBO = channelManageService.getChannel(channelId);
				sendMessage.sendBalance(channelBO.getDrawerName(), 10000);
			}
			channelManageService.updateChannelBalance(channelId, balance);
		}
	}

	@Override
	public void updateSynchronizedOrderStatus(String orderCode) {
		if (StringUtils.isBlank(orderCode)) {
			return;
		}
		int status = orderInfoDaoMapper.getOrderStatus(orderCode);
		if (status != 5 && status != 6 && status != 7) {
			LOGGER.info("订单状态是： 5:出票中；6：已出票；7：出票失败 才能同步订单状态：" + orderCode);
			throw new ServiceRuntimeException("出票中,已出票,出票失败,才能同步订单状态");
		}
		List<Integer> ticketStatus = ticketInfoDaoMapper.getTicketStatus(orderCode);
		int temp = 6;
		// -2出票失败;-1送票失败;0不出票;1待分配;2已分配;3已送票;4已出票
		status: for (Integer integer : ticketStatus) {
			switch (integer) {
			case 4:
				break;
			case 3:
			case 2:
			case 1:
				temp = 5;
				break;
			case -1:
			case -2:
				temp = 7;
				break status;
			default:
				break;
			}
		}
		if (status != temp) {
			List<OrderStatusBO> orders = new ArrayList<>();
			OrderStatusBO bo = new OrderStatusBO();
			bo.setOrderCode(orderCode);
			bo.setOrderStatus(temp);
			orders.add(bo);
			updateOrderStatusOutTicket(orders);
		}
	}

	@Override
	public String getChannelMaxNo(String channelId, String beforeNo) {
		return ticketInfoDaoMapper.getChannelMaxNo(channelId, beforeNo);
	}

	@Override
	public void sendOutTicketAlarm(int lotteryCode, String issue, String batchNum) {
		sendMessage.sendOutTicketAlarm(lotteryCode, issue, batchNum);
	}

	@Override
	public int getSendTicket(int lotteryCode, String ticketChannelId) {
		return ticketInfoDaoMapper.countSendTikcet(lotteryCode, ticketChannelId);
	}

	@Override
	public TicketBO getChannelTicketContent(String batchNum, String batchNumSeq) {
		return ticketInfoDaoMapper.getChannelTicketContent(batchNum, batchNumSeq);
	}

	@Override
	public List<String> channelCheck(List<DealerCheckBO> batchNums) {
		LOGGER.info("检票" + batchNums.toString());
		List<String> outing = new ArrayList<>();
		if (CollectionUtils.isEmpty(batchNums)) {
			return outing;
		}
		// 根据彩种，出票商打包检票订单
		Map<String, List<DealerCheckBO>> map = new HashMap<>();
		for (DealerCheckBO ticketBO : batchNums) {
			String key = ticketBO.getLotteryCode() + SymbolConstants.COMMA + ticketBO.getChannelId();
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<DealerCheckBO>());
			}
			map.get(key).add(ticketBO);
		}
		// 根据打包数据获取出票商进行检票
		for (Map.Entry<String, List<DealerCheckBO>> entry : map.entrySet()) {
			String[] str = entry.getKey().split(SymbolConstants.COMMA);
			ITicketChannel channel = handler.getChannelCheck(Integer.parseInt(str[0]),str[1]);
			try {
				if(channel != null){
					outing.addAll(channel.checkTicket(entry.getValue(), true));	
				}else{
					LOGGER.info("检票失败,不存在检票渠道" + entry.getValue());
				}
			} catch (Exception e) {
				LOGGER.info("检票失败" + entry.getValue(), e);
			}
		}
		return outing;
	}

	@Override
	public void updateChangeChannel(TicketBO ticketBO) {
		if(ticketBO.getChangeType() != 1){
			return ;
		}
		//不用新生成票逻辑
		/*if(ticketBO.getChangeId() == null){
			ticketBO.setChangeId(ticketBO.getId());
			ticketInfoDaoMapper.updateChangeTicket(ticketBO.getId(),ticketBO.getChangeId());
		}
		ticketBO.setTicketChange(ticketBO.getTicketChange() + "," + ticketBO.getChannelId());
		ticketInfoDaoMapper.updateTicketChannel(ticketBO.getOrderDetailId(),ticketBO.getChangeId(),ticketBO.getTicketChange());
		TicketInfoPO po = new TicketInfoPO(ticketBO);
		ticketInfoDaoMapper.addChangeTicket(po);
		ticketBO.setId(po.getId());*/
	}

	@Override
	public void updateSynchronizedOrderStatusById(String id) {
		String orderCode = ticketInfoDaoMapper.getOrderCode(id);
		updateSynchronizedOrderStatus(orderCode);
	}

	@Override
	public boolean checkTask(String project, String name) {
		int num = timeTaskSingleDaoMapper.updateExecute(project,name,TicketUtil.SERVICE_IP);
		if(num==1){
			return true;
		}
		return false;
	}
}
