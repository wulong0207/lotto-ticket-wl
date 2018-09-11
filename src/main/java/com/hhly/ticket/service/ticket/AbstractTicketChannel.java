package com.hhly.ticket.service.ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.AllowSendTimeBO;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.CheckBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;
import com.hhly.ticket.service.ticket.dealer.IDealer;
import com.hhly.ticket.util.DateUtil;

/**
 * @desc 出票商抽象出票抽象类
 * @author jiangwei
 * @date 2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractTicketChannel implements ITicketChannel {
	/**
	 * 保存未到送票时间的票信息
	 */
	protected DelayQueue<TicketBO> ticketQueue = new DelayQueue<>();
	/**
	 * 用来送票的线程池
	 */
	protected static ExecutorService pool = Executors.newFixedThreadPool(20);
	/**
	 * 渠道信息
	 */
	protected ChannelBO channelBO;
	/**
	 * 票转换接口
	 */
	protected IConvert convert;
	/**
	 * 送票接口
	 */
	protected IDealer dealer;
	/**
	 * 停售子玩法
	 */
	private List<String> stopLottery;

	private Map<String, List<AllowSendTimeBO>> allowSendTimeMap;
	/**
	 * 每批送票数
	 */
	protected int sendEachBatch;
	/**
	 * 随机数(用来计算送票时间种子，让不同彩种能平均分配在时间戳上,是系统压力平分)
	 * 比如：如果都是10秒送票间隔（彩种1：10,20,30...，彩种2：12,22,32...）
	 */
	private int random;
	/**
	 * 送票间隔
	 */
	private int[] sendLaw;
	/**
	 * 该渠道送票总金额
	 */
	private AtomicInteger money = new AtomicInteger(0);
	/**
	 * 该渠道（规定截止时间）送票总票数
	 */
	private AtomicInteger ticketCount = new AtomicInteger(0);
	/**
	 * 权重分配分割时间，大于30分钟截止按金额，否则按照票数量
	 */
	private static final int EXCISION_TIME = 30 * 60 * 1000;
	/**
	 * 上一次按票张数送票时间
	 */
	private long endTicketCountTime = 0;
	//最小金额
	private double min;
	//最大金额
	private double max;

	public AbstractTicketChannel(IConvert convert, IDealer dealer, ChannelBO channelBO) {
		this.convert = convert;
		this.dealer = dealer;
		this.channelBO = channelBO;
		/*if(channelBO.getThreadCount() <= 0){
			 channelBO.setThreadCount(1);
		}
		this.pool = Executors.newFixedThreadPool(channelBO.getThreadCount());*/
		allowSendTimeMap = splitAllowSendTime(channelBO.getAllowSendTime());
		sendEachBatch = channelBO.getSendEachBatch();
		random = new Random().nextInt(1000);
		sendLaw = computeSendLaw(channelBO.getEndSendSpace());
		if(channelBO.getEndMoeny() == null){
			max = Integer.MAX_VALUE;
		}else{
			max = channelBO.getEndMoeny().doubleValue();
		}
		if(channelBO.getStartMoney() != null){
			min = channelBO.getStartMoney().doubleValue();
		}
	}
	
	/**
	 * 获取渠道分配票的分割时间（用于判断是按票数还是按金额分配）
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 上午9:48:41
	 * @return
	 */
	public  static  int getExcisionTime(){
		return EXCISION_TIME;
	}
	
	@Override
	public int[] getSendLaw() {
		return sendLaw;
	}


	@Override
	public int getRandom() {
		return this.random;
	}

	/**
	 * 送票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月5日 上午11:05:48
	 */
	protected abstract void sendTicket();

	@Override
	public String getChannelId() {
		return channelBO.getTicketChannelId();
	}

	@Override
	public List<String> getStopLottery() {
		if (stopLottery == null) {
			stopLottery = stopLotteryInit();
		}
		return stopLottery;
	}
	
	@Override
	public long checkOutTime(long outTime, int interval) {
		Date time = new Date(outTime);
		String week = String.valueOf(DateUtil.dayForWeek(time));
		List<AllowSendTimeBO> list = allowSendTimeMap.get(week);
		if (CollectionUtils.isEmpty(list)) {
			return -1;
		}
		// 出票时间对应当天的秒
		long second = (outTime / 1000 + 3600 * 8) % 86400;
		//最快当天能送票时间秒数
		long minSecond = -1;
		for (AllowSendTimeBO allowSendTimeBO : list) {
			if (second >= allowSendTimeBO.getStart() && second <= allowSendTimeBO.getEnd()) {
				if (second - allowSendTimeBO.getStart() < interval) {
					//添加5秒让outTime与有效时间不同（用于判断阻塞列队的延迟时间）
					minSecond = second + 5;
				} else {
					minSecond = second;
				}
				break;
			}
			if (allowSendTimeBO.getStart() > second && (allowSendTimeBO.getStart() < minSecond || minSecond == -1)) {
				minSecond = allowSendTimeBO.getStart();
			}
		}
		if (minSecond == -1) {
			return -1;
		} else {
			return outTime + (minSecond - second) * 1000;
		}
	}
	
	@Override
	public List<String> checkTicket(List<DealerCheckBO> checkList,boolean immediately) {
		if(immediately){
			return dealer.checkTicket(checkList);
		}else{
			for (DealerCheckBO check : checkList) {
				CheckBO bo = new CheckBO(check.getLotteryCode(),check.getLotteryChildCode(), "[自动检票]", check.getBatchNum(), dealer, null);
				dealer.addCheckBatchNum(bo);
			}
			return new ArrayList<>();
		}
	}
	
	@Override
	public int getWeight() {
		return channelBO.getSendWeight();
	}
	
	/**
	 * 对送票进行分组
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午4:01:11
	 * @param ticketBO
	 * @return
	 */
	public String sendTicketKey(TicketBO ticketBO){
		StringBuffer sb = new StringBuffer(ticketBO.getChannelLotteryCode());
		if(ticketBO.getChannelPlayType() != null){
			sb.append(ticketBO.getChannelPlayType());
		}
		if(ticketBO.getLotteryCode() == 300 || ticketBO.getLotteryCode() == 301){
			
		}else{
			sb.append(ticketBO.getChannelLotteryIssue());
			sb.append(ticketBO.getLottoAdd());
		}
		return sb.toString();
	}
	
	@Override
	public int getTicketCount() {
		if((System.currentTimeMillis() - endTicketCountTime) > EXCISION_TIME){
			int num = getSendTicket();
			ticketCount.set(num);
		}
		return ticketCount.get();
	}
	/**
	 * 获取已送票，未出票数量
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月15日 上午10:03:44
	 * @return
	 */
    public abstract int getSendTicket();
    
	@Override
	public int getTicketMoney() {
		return money.get();
	}

	@Override
	public void addTicketMoney(int num) {
		money.addAndGet(num);
	}

	@Override
	public void addTicketCount(int num) {
		ticketCount.addAndGet(num);
		endTicketCountTime = System.currentTimeMillis();
	}


	@Override
	public int getDifference(ITicketChannel channel, ISource source) {
		int fw = channel.getWeight();
		int fn = source.get(channel);
		int num = source.get(this);
    	int current = this.getWeight() * fn / fw ;
    	return num - current;
	}
	
	
	@Override
	public double getChannelBalance() {
		return dealer.getDealerBalance();
	}

	/**
	 * 拆分送票时间（格式：1|02:00:00|04:00:00,2|01:00:00|03:00:00）
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月8日 上午10:28:03
	 * @param allowSendTime
	 * @return
	 */
	private Map<String, List<AllowSendTimeBO>> splitAllowSendTime(String allowSendTime) {
		Map<String, List<AllowSendTimeBO>> map = new HashMap<>();
		String[] sendTime = allowSendTime.split(SymbolConstants.COMMA);
		for (String str : sendTime) {
			String[] wt = str.split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
			String week = wt[0];
			int start = getSend(wt[1]);
			int end = getSend(wt[2]);
			AllowSendTimeBO bo = new AllowSendTimeBO(week, start, end);
			if (!map.containsKey(week)) {
				map.put(week, new ArrayList<AllowSendTimeBO>());
			}
			map.get(week).add(bo);
			int weekNum = Integer.parseInt(week);
			if (weekNum < 1 || weekNum > 7 || end < start) {
				throw new ServiceRuntimeException("渠道送票时间格式设置错误：" + str);
			}
		}
		return map;
	}

	/**
	 * 计算秒
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月8日 上午10:36:28
	 * @param time
	 *            02:00:00
	 * @return 秒
	 */
	private int getSend(String time) {
		String[] str = time.split(SymbolConstants.COLON);
		int num = Integer.parseInt(str[0]) * 3600 + Integer.parseInt(str[1]) * 60 + Integer.parseInt(str[2]);
		return num;
	}
	/**
	 * 初始化停售玩法
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月15日 下午12:27:36
	 * @return
	 */
	private List<String> stopLotteryInit() {
		List<String> list = new ArrayList<>();
		if (StringUtils.isNotBlank(channelBO.getStopLottery())) {
			list.addAll(Arrays.asList(channelBO.getStopLottery().split(SymbolConstants.COMMA)));
		}
		return list;
	}
	/**
	 * 计算送票间隔
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 上午11:16:37
	 * @param send
	 * @return
	 */
	private int[] computeSendLaw(String send){
		//默认送票间隔
		int [] sl = new int[]{10,300,10};
		if(StringUtils.isNotEmpty(send)){
			String[] content =send.split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
			if(content.length == 3){
				sl =new int[] { Integer.parseInt(content[0]), Integer.parseInt(content[1]),
						Integer.parseInt(content[2])};
			}
		}
		return sl;
		
	}

	@Override
	public int getDealerEndTime() {
		return channelBO.getDealerEndTime() * 1000;
	}

	@Override
	public boolean isIntegral() {
		return dealer.channelType() == 1;
	}

	@Override
	public boolean isContain(double min, double max) {
		return (min >= this.min && max <= this.max);
	}

	@Override
	public boolean isPassMode(TicketBO bo) {
		return  dealer.isPassMode(bo);
	}
	
	
	
	

}
