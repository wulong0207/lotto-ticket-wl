package com.hhly.ticket.service.ticket.dealer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
import com.hhly.ticket.base.exception.NotExistTicketRuntimeException;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.base.thread.ThreadPoolManager;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.CheckBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.DealerInfo;
import com.hhly.ticket.service.entity.MatchInfo;
import com.hhly.ticket.service.entity.MatchOdds;
import com.hhly.ticket.service.entity.ReceiptContent;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.util.CollectionUtil;

/**
 * @desc 抽象出票商接口
 * @author jiangwei
 * @date 2017年5月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractDealer implements IDealer {
	/**
	 * 订单服务
	 */
	protected IOrderService orderService;
	// 检票间隔20000毫秒（20秒）
	private static final int INTERVAL = 20000;

	protected static final Logger LOGGER = Logger.getLogger(AbstractDealer.class);
	// 检票列队
	private static DelayQueue<CheckBO> ticketQueue = new DelayQueue<>();

	protected DealerInfo dealerInfo;

	// 渠道类型0普通渠道，1积分兑换码渠道
	protected int channelType = 0;

	static {
		// 启动一个检票线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				check();
			}
		}, "检票线程").start();
	}

	public AbstractDealer(IOrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * 移除检票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 上午10:18:52
	 * @param batchNum
	 */
	public static void removeCheck(Collection<String> batchNums) {
		for (String batchNum : batchNums) {
			CheckBO bo = new CheckBO(batchNum);
			ticketQueue.remove(bo);
		}
	}

	/**
	 * 添加检票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 上午10:19:13
	 * @param batchNum
	 * @param time
	 */
	@Override
	public void addCheckBatchNum(CheckBO bo) {
		// 主动查票
		if (dealerInfo.getSearchAuto() == 1) {
			long delay = getFirstCheckTime();
			addCheckBatchNumToQueue(bo, delay, TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * 送票成功后默认检票时间（2分钟） 不同出票商可以重新该方法设置值
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 下午2:19:06
	 * @return 毫秒
	 */
	protected int getFirstCheckTime() {
		return 100_000;
	}

	/**
	 * 更新票出票状态， 检票或者出票商通知出票状态后更新票和订单装填 移除检票列队信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月2日 上午10:40:26
	 * @param tickets
	 */
	protected void updateOutTicket(List<TicketBO> tickets) {
		if (CollectionUtils.isEmpty(tickets)) {
			return;
		}
		removeCheck(orderService.updateOutTicket(tickets));
	}

	@Override
	public void addAlarmInfo(int lotteryCode, String issue, String batchNum) {
		orderService.sendOutTicketAlarm(lotteryCode, issue, batchNum);
	}

	/**
	 * 送票成功
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 下午3:27:12
	 * @param tickets
	 */
	protected void sendTicketSuccess(List<TicketBO> tickets) {
		for (TicketBO ticketBO : tickets) {
			doTicketSuccess(ticketBO);
		}
	}

	/**
	 * 处理出票成功
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月19日 上午11:12:06
	 * @param ticketBO
	 */
	protected void doTicketSuccess(TicketBO ticketBO) {
		ticketBO.setTicketStatus(TicketStatus.SEND.getValue());
		ticketBO.setTicketRemark("送票成功");
	}

	/**
	 * 获取备注
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月19日 上午10:59:53
	 * @param code
	 * @return
	 */
	protected String getRemark(String code) {
		return "";
	}

	/**
	 * 送票失败
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月19日 上午10:52:12
	 * @param tickets
	 * @param code
	 */
	protected void sendTicketFail(List<TicketBO> tickets, String code) {
		for (TicketBO ticketBO : tickets) {
			doTicketFail(ticketBO, code);
		}
	}

	/**
	 * 票出票失败
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月19日 上午11:10:28
	 * @param ticketBO
	 * @param code
	 */
	protected void doTicketFail(TicketBO ticketBO, String code) {
		ticketBO.setTicketStatus(TicketStatus.SEND_FAIL.getValue());
		ticketBO.setChannelRemark(getRemark(code));
		ticketBO.setReceiptContent("送票格式：" + ticketBO.getChannelTicketContent());
		ticketBO.setTicketRemark("送票出票商失败");
	}

	/**
	 * 取阻塞列队中的票进行检票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 上午10:15:24
	 */
	private static void check() {
		List<CheckBO> list = new ArrayList<>();
		long time = 0;
		for (;;) {
			CheckBO bo = null;
			try {
				// 每隔20秒进行一次检票超作
				if (list.isEmpty()) {
					bo = ticketQueue.take();
					time = System.currentTimeMillis() + INTERVAL;
				} else {
					bo = ticketQueue.poll(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
				}
			} catch (InterruptedException e) {
				LOGGER.error("检票获取金票信息失败", e);
			}
			if (bo != null) {
				list.add(bo);
			}
			// 判断是否超过时间
			if (time > System.currentTimeMillis()) {
				continue;
			}
			// 分配检票,不同渠道商进行打包
			Map<IDealer, List<DealerCheckBO>> map = new HashMap<>();
			long now = System.currentTimeMillis();
			final Map<String, CheckBO> endCheckTime = new HashMap<>();
			for (CheckBO checkBO : list) {
				if (checkBO.getEndCheckTime() != null && checkBO.getEndCheckTime().getTime() <= now) {
					continue;
				}
				if (!map.containsKey(checkBO.getDealer())) {
					map.put(checkBO.getDealer(), new ArrayList<DealerCheckBO>());
				}
				DealerCheckBO dealerCheckBO = new DealerCheckBO(checkBO.getLotteryCode(), checkBO.getBatchNum(),checkBO.getLotteryChildCode());
				map.get(checkBO.getDealer()).add(dealerCheckBO);
				endCheckTime.put(checkBO.getBatchNum(), checkBO);
			}
			for (final Map.Entry<IDealer, List<DealerCheckBO>> entry : map.entrySet()) {
				ThreadPoolManager.submit(new Runnable() {
					@Override
					public void run() {
						LOGGER.info("检票：" + entry.getValue().toString());
						List<String> result = null;
						try {
							result = entry.getKey().checkTicket(entry.getValue());
						} catch (Exception e) {
							LOGGER.error("检票异常", e);
							result = new ArrayList<>();
							for (DealerCheckBO bo : entry.getValue()) {
								result.add(bo.getBatchNum());
							}
						}
						// 如果检票失败6分钟后再次尝试检票,
						// 如果小于6分钟并且大于1分钟那就在截止前1分钟检票
						// 小于一分钟截止检票
						for (String batchNum : result) {
							CheckBO bo = endCheckTime.get(batchNum);
							if (bo.getEndCheckTime() == null) {
								continue;
							}
							addCheckBatchNumToQueue(bo, 360, TimeUnit.SECONDS);
							// 竞彩第一次没有出票成功报警，其它离截止5分钟给报警
							if (isFirstCheckAlarm(bo.getLotteryCode())
									|| (bo.getEndCheckTime().getTime() - System.currentTimeMillis()) < 300_000) {
								entry.getKey().addAlarmInfo(bo.getLotteryCode(), bo.getIssue(), batchNum);
							}
						}
					}
				});
			}
			list.clear();
		}
	}

	private static boolean isFirstCheckAlarm(int lotteryCode) {
		if (lotteryCode == 300 || lotteryCode == 301) {
			return true;
		}
		return false;
	}

	/**
	 * 添加检票时间
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 下午2:36:16
	 * @param batchNum
	 * @param delay
	 * @param unit
	 */
	private static void addCheckBatchNumToQueue(CheckBO bo, long delay, TimeUnit unit) {
		long checkTime = computeCheckTime(bo.getEndCheckTime(), delay, unit);
		if (checkTime == -1) {
			// 不存在合法的检票时间
			return;
		}
		if (StringUtils.isBlank(bo.getBatchNum())) {
			LOGGER.info("检票不存在批次号", new Exception());
			return;
		}
		bo.setCheckTime(checkTime);
		ticketQueue.offer(bo);
	}

	/**
	 * 计算检票时间
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月12日 下午2:58:01
	 * @param endCheckTime
	 *            截止时间
	 * @param delay
	 *            间隔
	 * @param unit
	 *            时间单位
	 * @return
	 */
	private static long computeCheckTime(Date endCheckTime, long delay, TimeUnit unit) {
		long checkTime = -1;
		if (endCheckTime != null) {
			// 计算下一次检票时间
			long millisDelay = unit.toMillis(delay);
			long mill = endCheckTime.getTime() - System.currentTimeMillis();
			if (mill > 60_000) {// 离截止小于一分钟结束检票
				if (mill < millisDelay) {
					millisDelay = mill - 60_000;
				}
				checkTime = System.currentTimeMillis() + millisDelay;
			}
		} else {
			checkTime = System.currentTimeMillis() + unit.toMillis(delay);
		}
		return checkTime;
	}

	/**
	 * 获取出票商检票响应
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月17日 上午11:59:11
	 * @param list
	 * @return
	 */
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		throw new ServiceRuntimeException("获取出票商统一检票响应未实现");
	}

	/**
	 * @see 默认检票实现
	 */
	@Override
	public List<String> checkTicket(List<DealerCheckBO> checkBO) {
		List<String> result = new ArrayList<>();
		if (CollectionUtils.isEmpty(checkBO)) {
			return result;
		}
		List<List<DealerCheckBO>> subList = CollectionUtil.subList(checkBO, dealerInfo.getSearchMaxTicket());
		for (List<DealerCheckBO> list : subList) {
			ICheckResponse msg = sendCheckResponse(list);
			result.addAll(handleCheckTicket(msg));
		}
		return result;
	}

	/**
	 * 处理检票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月17日 上午10:55:34
	 * @param msg
	 * @return
	 */
	protected Collection<? extends String> handleCheckTicket(ICheckResponse response) {
		List<String> result = new ArrayList<>();
		if (!response.isSuccess()) {
			if (response.isExist()) {
				LOGGER.info("出票商检票返回错误代码:code:" + response.getCode() + ";message:" + response.getMessage());
			}
			return result;
		}
		if (CollectionUtils.isEmpty(response.getTicket())) {
			LOGGER.info("出票商检票查询不到该票信息");
			return result;
		}
		List<TicketBO> haveResultTickets = new ArrayList<>();
		for (ICheckTicket ticket : response.getTicket()) {
			try {
				TicketBO bo = handleCheckOrderResult(ticket);
				if (bo == null) {
					result.add(ticket.getBatchNum());
				} else {
					haveResultTickets.add(bo);
				}
			} catch (NotExistTicketRuntimeException e) {
				LOGGER.info(e.getMessage());
			}
		}
		updateOutTicket(haveResultTickets);
		return result;
	}

	/**
	 * 处理检票订单信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月17日 下午12:01:26
	 * @param ticket
	 * @return
	 */
	private TicketBO handleCheckOrderResult(ICheckTicket ticket) {
		if (ticket.isNotExist()) {
			throw new NotExistTicketRuntimeException("不存在票号:" + ticket.getBatchNum());
		}
		if (!ticket.isOutFial() && !ticket.isOutSuccess()) {
			return null;
		}
		TicketBO bo = new TicketBO();
		bo.setBatchNum(ticket.getBatchNum());
		bo.setBatchNumSeq(ticket.getBatchNumSeq());
		bo.setOfficialNum(ticket.getOfficialNum());
		bo.setReceiptContent(ticket.getReceiptContent());// 回执
		if (StringUtils.isNotEmpty(ticket.getThirdNum())) {
			bo.setThirdNum(ticket.getThirdNum());
		}
		if (ticket.isOutSuccess()) {
			bo.setTicketStatus(TicketStatus.OUT_TICKET.getValue());
			ReceiptContent rc = getOdd(bo, ticket.getOdd());
			bo.setReceiptContent(rc.getReceiptContent());
			bo.setReceiptContentDetail(rc.getReceiptContentDetail());
			bo.setTicketRemark("出票成功");
		} else if (ticket.isOutFial()) {
			bo.setTicketStatus(TicketStatus.ERROR.getValue());
			bo.setChannelRemark("出票商出票失败" + getRemark(ticket.getCode()) + "|" + ticket.getMessage());
			bo.setTicketRemark("出票失败");
		}
		return bo;
	}

	/**
	 * 处理竞彩赔率
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 下午3:52:32
	 * @param odd
	 * @return 1.340-2.270A2.850
	 */
	protected ReceiptContent getOdd(TicketBO bo, String odd) {
		ReceiptContent rc = new ReceiptContent();
		rc.setReceiptContent(odd);
		if (StringUtils.isEmpty(odd)) {
			return rc;
		}
		TicketBO channelContent = orderService.getChannelTicketContent(bo.getBatchNum(), bo.getBatchNumSeq());
		if (channelContent == null || StringUtils.isEmpty(channelContent.getChannelTicketContent())) {
			LOGGER.info(bo.getBatchNum() + "票不存在送票格式");
			return rc;
		}
		// 判断是否竞技彩
		if (channelContent.getLotteryCode() != 300 && channelContent.getLotteryCode() != 301
				&& channelContent.getLotteryCode() != 308 && channelContent.getLotteryCode() != 309) {
			return rc;
		}
		LOGGER.info(new StringBuffer("解析赔率").append(bo.getBatchNum()).append("|")
				.append(channelContent.getChannelTicketContent()).append("|").append(odd).toString());
		rc.setReceiptContent(doMatchOdd(odd, channelContent.getChannelTicketContent()));
		rc.setReceiptContentDetail(receiptDetail(channelContent.getLotteryCode(), channelContent.getTicketContent(),
				rc.getReceiptContent()));
		return rc;
	}

	/**
	 * 解析回执内容
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年11月23日 下午12:25:02
	 * @param content
	 * @param text
	 *            出票商返回
	 * @return
	 */
	private String receiptDetail(int lotteryCode, String content, String text) {
		if (lotteryCode == 308 || lotteryCode == 309) {
			String[] str = org.springframework.util.StringUtils.tokenizeToStringArray(content, "@^");
			return str[0] + "@" + String.format("%.2f", Double.valueOf(text)) + "^" + str[2];
		} else {
			String[] all = text.split("@");
			String[] odds = all[0].split("[-A]");
			String[] target = null;
			if (all.length > 1) {
				target = all[1].split("B");
			}
			StringBuilder sb = new StringBuilder();
			StringBuilder temp = new StringBuilder();
			int num = 0;
			int oddnum = 0;
			boolean isAdd = true;
			for (char c : content.toCharArray()) {
				switch (c) {
				case '[':
					sb.append(c);
					isAdd = false;
					break;
				case ']':
					if (target == null || lotteryCode == 300) {
						sb.append(temp);
					} else {
						sb.append(target[num]);
					}
					sb.append(c);
					temp.setLength(0);
					isAdd = true;
					break;
				case '@':
					sb.append(c);
					isAdd = false;
					break;
				case ')':
				case ',':
					sb.append(String.format("%.2f", Double.valueOf(odds[oddnum]))).append(c);
					oddnum++;
					temp.setLength(0);
					isAdd = true;
					break;
				case '|':
					num++;
					sb.append(c);
					isAdd = true;
					break;
				default:
					if (isAdd) {
						sb.append(c);
					} else {
						temp.append(c);
					}
					break;
				}
			}
			return sb.toString();
		}

	}

	/**
	 * 处理出票商返回赔率
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 下午6:11:28
	 * @param odd
	 * @param channelContent
	 * @return
	 */
	protected String doMatchOdd(String odd, String channelContent) {
		return odd;
	}

	@Override
	public int channelType() {
		return channelType;
	};

	/**
	 * 竞技彩赔率解析
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月16日 下午12:00:20
	 * @param odd
	 * @param channelContent
	 * @return
	 */
	public String doMatchOddSport(String odd, String channelContent) {
		StringBuffer sb = new StringBuffer();
		StringBuffer tg = new StringBuffer();
		List<MatchInfo> matchInfos = getMatchInfo(channelContent);
		Map<String, MatchOdds> moi = getMatchOddInfo(odd);
		for (MatchInfo mi : matchInfos) {
			if (sb.length() > 0) {
				sb.append("-");
			}
			String[] play = mi.getPlay().split("/");
			for (int i = 0; i < play.length; i++) {
				String key = mi.getNumber() + "_" + play[i];
				MatchOdds matchOdds = moi.get(key);
				if (i > 0) {
					sb.append("A");
				}else{
					if (tg.length() > 0) {
						tg.append("B");
					}
					if (StringUtils.isEmpty(matchOdds.getTarget())) {
						tg.append("#");
					} else {
						double r = Double.parseDouble(matchOdds.getTarget());
						String tar = String.format("%.1f", r);
						if (r > 0 && r < 100) {
							tar = "+" + tar;
						}
						tg.append(tar);
					}
				}
				sb.append(matchOdds.getOdd());
			}
		}
		return sb.append("@").append(tg).toString();
	}

	/**
	 * 赔率信息 key 比赛场次+"_"+玩法
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月16日 下午12:03:08
	 * @param odd
	 * @return
	 */
	protected Map<String, MatchOdds> getMatchOddInfo(String odd) {
		throw new ServiceRuntimeException("竞技彩未实现赔率解析");
	}

	/**
	 * 送票场次信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月16日 下午12:03:10
	 * @param channelContent
	 * @return
	 */
	protected List<MatchInfo> getMatchInfo(String channelContent) {
		throw new ServiceRuntimeException("竞技彩未实现赔率解析");
	}

	@Override
	public boolean isPassMode(TicketBO bo) {
		return true;
	}
	
	

}
