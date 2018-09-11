package com.hhly.ticket.service.ticket.dealer.hengpeng.cathectic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.DealerInfo;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.IXml;
import com.hhly.ticket.service.ticket.dealer.AbstractDealer;
import com.hhly.ticket.service.ticket.dealer.INotify;
import com.hhly.ticket.service.ticket.dealer.hengpeng.AbstractMsg;
import com.hhly.ticket.service.ticket.dealer.hengpeng.CathecticRequestMsg;
import com.hhly.ticket.service.ticket.dealer.hengpeng.CheckTicketRequestMsg;
import com.hhly.ticket.service.ticket.dealer.hengpeng.CheckTicketResponseMsg;
import com.hhly.ticket.service.ticket.dealer.hengpeng.Header;
import com.hhly.ticket.service.ticket.dealer.hengpeng.HengPengUtil;
import com.hhly.ticket.service.ticket.dealer.hengpeng.Issue;
import com.hhly.ticket.service.ticket.dealer.hengpeng.IssueRequestMsg;
import com.hhly.ticket.service.ticket.dealer.hengpeng.IssueResponseMsg;
import com.hhly.ticket.service.ticket.dealer.hengpeng.NotifyTicketResponseMsg;
import com.hhly.ticket.service.ticket.dealer.hengpeng.ResponseMsg;
import com.hhly.ticket.service.ticket.dealer.hengpeng.UserProfile;
import com.hhly.ticket.service.ticket.dealer.hengpeng.request.CathecticTicket;
import com.hhly.ticket.service.ticket.dealer.hengpeng.request.CheckTicket;
import com.hhly.ticket.service.ticket.dealer.hengpeng.response.CheckTicketResponse;
import com.hhly.ticket.service.ticket.dealer.hengpeng.response.IssueResponse;
import com.hhly.ticket.service.ticket.dealer.hengpeng.response.NotifyTicket;
import com.hhly.ticket.util.CollectionUtil;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.DateUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.PropertyUtil;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 恒朋出票商
 * @author jiangwei
 * @date 2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class HengPengDealer extends AbstractDealer implements INotify{
	// 出票商处理成功代码
	private static final String SUCCESS = "0000";
	// 投注中
	private static final String OUTING = "2052";
	//不存在票
	private static final String NOT_EXIST = "2049";
	// 渠道商id
	private String channelId;
	// 睿朗投注参数
	private static String USERNAME;
	private static String CARDNO;
	private static String MOBILE;
	private static String REALNAME;
	private static String EMAIL;
	private static String BONUSPHONE;
	// 消息递增数
	private static AtomicInteger atmoicInteger;

	static {
		// 投注用户信息
		USERNAME = PropertyUtil.getPropertyValue("application.properties", "hp_username");
		CARDNO = PropertyUtil.getPropertyValue("application.properties", "hp_cardno");
		MOBILE = PropertyUtil.getPropertyValue("application.properties", "hp_mobile");
		REALNAME = PropertyUtil.getPropertyValue("application.properties", "hp_realname");
		EMAIL = PropertyUtil.getPropertyValue("application.properties", "hp_email");
		BONUSPHONE = PropertyUtil.getPropertyValue("application.properties", "hp_bonusphone");

	}

	public HengPengDealer(ChannelBO bo, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(bo.getDrawerAccount());
		dealerInfo.setAccountPassword(DESUtil.decrypt(bo.getAccountPassword()));
		dealerInfo.setSendUrl(bo.getSendUrl());
		dealerInfo.setSearchMaxTicket(bo.getSearchMaxTicket() > 5 ? 5 : bo.getSearchMaxTicket());
		dealerInfo.setSearchAuto(bo.getSearchAuto());
		super.dealerInfo = dealerInfo;
		channelId = bo.getTicketChannelId();
	}
	
	/**
	 * 初始化 INotify 构造器
	 * 
	 * @param orderService
	 * @param alarmInfoService
	 */
	public HengPengDealer(String deawerAccount, String accountPassword, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(deawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}
	/**
	 * @see 最多可以携带500票
	 */
	@Override
	public boolean sendTicket(List<TicketBO> tickets) {
		if (CollectionUtils.isEmpty(tickets)) {
			return false;
		}
		//封装xml
		List<CathecticTicket> cathecTictickets = new ArrayList<>();
		for (TicketBO bo : tickets) {
			Issue issue = new Issue(bo.getChannelLotteryCode(), bo.getChannelLotteryIssue());
			UserProfile user = new UserProfile(USERNAME, EMAIL, CARDNO, MOBILE, REALNAME, BONUSPHONE);
			List<String> anteCode = Arrays.asList(bo.getChannelTicketContent().split(";"));
			CathecticTicket cathecTicticket = new CathecticTicket(getId(), bo.getChannelPlayType(), bo.getTicketMoney(),
					bo.getMultipleNum(), issue, user, anteCode);
			cathecTictickets.add(cathecTicticket);
			bo.setBatchNum(cathecTicticket.getId());
			bo.setBatchNumSeq("1");
		}
		CathecticRequestMsg msg = new CathecticRequestMsg(cathecTictickets);
		msg.setId(getId());
		String type = getCathecticTransactionType(tickets.get(0).getLotteryCode());
		Header header = getHeader(type, msg.getId(), msg.getBody());
		msg.setHeader(header);
		//请求，处理结果
		String result = responseDealer(msg);
		ResponseMsg responseMsg = getResponseMsg(ResponseMsg.class, result);
		String code = responseMsg.getBody().getResponse().getCode();
		if (SUCCESS.equals(code)) {
			sendTicketSuccess(tickets);
			return true;
		} else {
			sendTicketFail(tickets, code);
			return false;
		}
	}


	@Override
	protected String getRemark(String code) {
		return code + ":" + HengPengUtil.getErrorMessage(code);
	}

	@Override
	public List<String> checkTicket(List<DealerCheckBO> checkBO) {
		List<String> result = new ArrayList<>();
		List<String> sportList = new ArrayList<>();
		List<String> otherList = new ArrayList<>();
		for (DealerCheckBO dealerCheckBO : checkBO) {
			if (isSport(dealerCheckBO.getLotteryCode())) {
				sportList.add(dealerCheckBO.getBatchNum());
			} else {
				otherList.add(dealerCheckBO.getBatchNum());
			}
		}
		result.addAll(handleCheck(otherList, "105"));
		result.addAll(handleCheck(sportList, "176"));
		return result;
	}

	/**
	 * 处理检票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 下午4:05:45
	 * @param otherList
	 * @return
	 */
	private List<String> handleCheck(List<String> otherList, String transactionType) {
		List<String> result = new ArrayList<>();
		List<List<String>> subList = CollectionUtil.subList(otherList, dealerInfo.getSearchMaxTicket());
		List<TicketBO> haveResultTickets = new ArrayList<>();
		for (List<String> list : subList) {
			List<CheckTicket> tickets = pottingTicket(list);
			CheckTicketRequestMsg msg = new CheckTicketRequestMsg(tickets);
			//判断是什么类型检票
			if ("105".equals(transactionType)) {
				CheckTicketResponseMsg responseMsg = sendCheck(msg, transactionType, CheckTicketResponseMsg.class);
				CheckTicketResponse checkTicketResponse = responseMsg.getBody().getResponse();
				if (!SUCCESS.equals(checkTicketResponse.getCode())) {
					result.addAll(list);
					continue;
				}
				List<com.hhly.ticket.service.ticket.dealer.hengpeng.response.CheckTicket> ticket = checkTicketResponse
						.getTicketQueryResult().getTicket();
				//处理票信息
				for (com.hhly.ticket.service.ticket.dealer.hengpeng.response.CheckTicket checkTicket : ticket) {
					try {
						TicketBO bo = handleCheckOrderResult(checkTicket);
						if (bo == null) {
							result.add(checkTicket.getId());
						} else {
							haveResultTickets.add(bo);
						}
					} catch (ServiceRuntimeException e) {
						LOGGER.info(e.getMessage());
					}
				}
			} else {
				throw new ServiceRuntimeException("未实现竞技彩检票");
			}
		}
		updateOutTicket(haveResultTickets);
		return result;
	}

	/**
	 * 封装查票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 下午4:44:29
	 * @param list
	 * @return
	 */
	private List<CheckTicket> pottingTicket(List<String> list) {
		List<CheckTicket> tickets = new ArrayList<>();
		for (String id : list) {
			tickets.add(new CheckTicket(id));
		}
		return tickets;
	}

	/**
	 * 处理出票商票结果
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 下午4:36:07
	 * @param checkTicket
	 * @return
	 */
	private TicketBO handleCheckOrderResult(NotifyTicket notifyTicket) {
		String status = notifyTicket.getStatus();
		if (OUTING.equals(notifyTicket.getStatus())) {
			return null;
		}
		if(NOT_EXIST.equals(notifyTicket.getStatus())){
			throw new ServiceRuntimeException("不存在票号:" + notifyTicket.getId());
		}
		TicketBO bo = new TicketBO();
		bo.setBatchNum(notifyTicket.getId());
		bo.setBatchNumSeq("1");
		bo.setThirdNum(notifyTicket.getTicketSerialNo());// 第三方票号
		bo.setOfficialNum("");// 官方票号
		bo.setReceiptContent("");// 回执
		String message = "";
		if(notifyTicket  instanceof com.hhly.ticket.service.ticket.dealer.hengpeng.response.CheckTicket){
			message = ((com.hhly.ticket.service.ticket.dealer.hengpeng.response.CheckTicket)notifyTicket).getMessage();
		}
		if (SUCCESS.equals(status)) {
			bo.setTicketStatus(TicketStatus.OUT_TICKET.getValue());
			bo.setChannelRemark(message);
			bo.setTicketRemark("出票成功");
		} else {
			bo.setTicketStatus(TicketStatus.ERROR.getValue());
			bo.setChannelRemark(status + ":" + HengPengUtil.getErrorMessage(status) + "|" + message);
			bo.setTicketRemark("出票失败");
		}
		return bo;
	}

	@Override
	public double getDealerBalance() {
		return -1;
	}
	
	@Override
	public String notifyOutTicket(String msg) {
		NotifyTicketResponseMsg responseMsg = getResponseMsg(NotifyTicketResponseMsg.class, msg);
		List<NotifyTicket> tickets = responseMsg.getBody().getTicketNotify().getTicket();
		List<TicketBO> haveResultTickets = new ArrayList<>();
		for (NotifyTicket notifyTicket : tickets) {
			TicketBO bo = handleCheckOrderResult(notifyTicket);
			haveResultTickets.add(bo);
		}
		updateOutTicket(haveResultTickets);
		return getSuccessReuslt("504","0000","成功,系统处理正常");
	}
	/**
	 * @see 0:返回类型，1：编码，2，消息
	 */
	@Override
	public String getSuccessReuslt(String ... code) {
		if(code.length != 3){
			throw new ServiceRuntimeException("获取响应结果参数错误");
		}
		ResponseMsg msg = new ResponseMsg(code[1],code[2]);
		msg.setId(getId());
		Header header = getHeader(code[0], msg.getId(), msg.getBody());
		msg.setHeader(header);
		return msg.toXml();
	}
	/**
	 * 查询彩期
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月14日 上午11:41:49
	 * @param code
	 * @param issue
	 * @return
	 */
	public List<com.hhly.ticket.service.ticket.dealer.hengpeng.response.Issue> searchIssue(Lottery lottery,String issue){
		String code = HengPengUtil.getCode(lottery);
		IssueRequestMsg  msg = new IssueRequestMsg(code,issue);
		msg.setId(getId());
		Header header = getHeader("102", msg.getId(), msg.getBody());
		msg.setHeader(header);
		String result = responseDealer(msg);
		IssueResponseMsg responseMsg = getResponseMsg(IssueResponseMsg.class, result);
		IssueResponse issueResponse = responseMsg.getBody().getResponse();
		if(SUCCESS.equals(issueResponse.getCode())){
			return issueResponse.getIssue();
		}
		return null;
	}
	
	/**
	 * 解析出票商返回结果 验证出票商返回加密是否正确
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午4:32:29
	 * @param cla
	 *            解析类
	 * @param xml
	 *            结果xml
	 * @return
	 */
	protected <T extends AbstractMsg> T getResponseMsg(Class<T> cla, String xml) {
		String md5;
		T t ;
		try {
			int num = xml.indexOf("transMessage=");
			if(num != -1){
				xml = xml.substring(num+13);
			}
			t = cla.newInstance().fromXML(xml);
			int start = xml.indexOf("<body>");
			int end = xml.indexOf("</body>") + 7;
			String body = xml.substring(start, end);
			md5 = getMd5(t.getId(), t.getHeader().getTimestamp(), body);
		} catch (Exception e) {
			throw new ServiceRuntimeException("xml格式转换错误" + xml, e);
		}
		if (Objects.equals(md5, t.getHeader().getDigest())) {
			return t;
		} else {
			throw new ServiceRuntimeException("加密验证错误：" + xml);
		}
	}

	/**
	 * 认证验证对象
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 上午11:55:35
	 * @param drawerAccount
	 * @param cmd
	 * @param md
	 * @return
	 */
	protected <T extends IXml> Header getHeader(String transactionType, String messageId, T body) {
		Header header = new Header();
		header.setMessengerID(dealerInfo.getDeawerAccount());
		header.setTransactionType(transactionType);
		header.setTimestamp(DateUtil.getNow(DateUtil.DATE_FORMAT_NUM));
		header.setDigest(getMd5(messageId, header.getTimestamp(), body));
		return header;
	}

	/**
	 * 请求
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午12:23:41
	 * @param msg
	 * @return
	 */
	protected <T extends AbstractMsg> String responseDealer(T msg) {
		Map<String, String> param = new HashMap<>();
		param.put("transType", msg.getHeader().getTransactionType());
		String msgStr = msg.toXml();
		param.put("transMessage", msgStr);
		String result = null;
		try {
			LOGGER.info("恒朋出票商请求" + msgStr);
			result = HttpUtil.doPostCharset(dealerInfo.getSendUrl(),"GBK", param);
			LOGGER.info("恒朋出票商响应" + result);
		} catch (IOException | URISyntaxException e) {
			LOGGER.error(e);
			result = e.getMessage();
		}
		return result;
	}

	/**
	 * body字符串加密
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午5:46:24
	 * @param body
	 * @return
	 */
	protected String getMd5(String id, String timestamp, String body) {
		// 消息编号+时间戳+投注代理密码+消息体
		String md5Str = id + timestamp + dealerInfo.getAccountPassword() + body;
		return Md5Util.md5_32(md5Str, "GBK");
	}

	/**
	 * 加密
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午12:17:41
	 * @param body
	 * @return
	 */
	protected <T extends IXml> String getMd5(String id, String timestamp, T body) {
		String bodyStr = body.toXml().replace("<?xml version=\"1.0\" ?>", "");
		return getMd5(id, timestamp, bodyStr);
	}

	/**
	 * 获取编号
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 上午11:00:38
	 * @return
	 */
	private String getId() {
		// 初始化递增数
		if (atmoicInteger == null) {
			synchronized (HengPengDealer.class) {
				if (atmoicInteger == null) {
					String beforeNo = dealerInfo.getDeawerAccount() + DateUtil.getNow(DateUtil.DATE_FORMAT_NO_LINE)
							+ TicketUtil.SERVICE_ID_ONE;
					String maxNum = orderService.getChannelMaxNo(channelId, beforeNo);
					if (StringUtils.isBlank(maxNum)) {
						atmoicInteger = new AtomicInteger(1000);
					} else {
						atmoicInteger = new AtomicInteger(Integer.parseInt(maxNum.substring(beforeNo.length()))+1000);
					}
				}
			}
		}
		int num = atmoicInteger.incrementAndGet();
		if (num == 9999000) {
			num = atmoicInteger.getAndSet(0);
		}
		StringBuilder sb = new StringBuilder(dealerInfo.getDeawerAccount());
		sb.append(DateUtil.getNow(DateUtil.DATE_FORMAT_NO_LINE));
		sb.append(TicketUtil.SERVICE_ID_ONE);
		String str = String.valueOf(num);
		for (int i = str.length(); i < 7; i++) {
			sb.append("0");
		}
		sb.append(str);
		return sb.toString();
	}

	/**
	 * 获取投注请求接口
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 下午3:40:31
	 * @param lotteryCode
	 * @return
	 */
	private String getCathecticTransactionType(int lotteryCode) {
		if (isSport(lotteryCode)) {
			return "174";
		}
		return "103";
	}

	/**
	 * 是否是竞彩
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 下午3:45:45
	 * @param lootteryCode
	 * @return
	 */
	private boolean isSport(int lootteryCode) {
		if (lootteryCode == 300 || lootteryCode == 301) {
			return true;
		}
		return false;
	}

	/**
	 * 发送检票请求
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 下午4:04:29
	 * @param msg
	 * @param transactionType
	 * @param cla
	 * @return
	 */
	private <T extends AbstractMsg> T sendCheck(CheckTicketRequestMsg msg, String transactionType, Class<T> cla) {
		msg.setId(getId());
		Header header = getHeader(transactionType, msg.getId(), msg.getBody());
		msg.setHeader(header);
		String resposeResult = responseDealer(msg);
		T responseMsg = getResponseMsg(cla, resposeResult);
		return responseMsg;
	}
}
