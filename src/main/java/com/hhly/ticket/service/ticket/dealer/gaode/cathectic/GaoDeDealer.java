package com.hhly.ticket.service.ticket.dealer.gaode.cathectic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.DealerInfo;
import com.hhly.ticket.service.entity.MatchInfo;
import com.hhly.ticket.service.entity.MatchOdds;
import com.hhly.ticket.service.entity.ReceiptContent;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.IXml;
import com.hhly.ticket.service.ticket.dealer.AbstractDealer;
import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.INotify;
import com.hhly.ticket.service.ticket.dealer.gaode.AbstractMsg;
import com.hhly.ticket.service.ticket.dealer.gaode.BalanceRequestMsg;
import com.hhly.ticket.service.ticket.dealer.gaode.BalanceResponseMsg;
import com.hhly.ticket.service.ticket.dealer.gaode.CathecticRequestMsg;
import com.hhly.ticket.service.ticket.dealer.gaode.CathecticResponseMsg;
import com.hhly.ticket.service.ticket.dealer.gaode.CheckRequestMsg;
import com.hhly.ticket.service.ticket.dealer.gaode.CheckResponseMsg;
import com.hhly.ticket.service.ticket.dealer.gaode.GaoDeUtil;
import com.hhly.ticket.service.ticket.dealer.gaode.Header;
import com.hhly.ticket.service.ticket.dealer.gaode.ResponseNotifyMsg;
import com.hhly.ticket.service.ticket.dealer.gaode.request.BalanceBody;
import com.hhly.ticket.service.ticket.dealer.gaode.request.BjdcCathecticBody;
import com.hhly.ticket.service.ticket.dealer.gaode.request.CathecticBody;
import com.hhly.ticket.service.ticket.dealer.gaode.request.CathecticTicket;
import com.hhly.ticket.service.ticket.dealer.gaode.request.CheckBody;
import com.hhly.ticket.service.ticket.dealer.gaode.request.CheckTicket;
import com.hhly.ticket.service.ticket.dealer.gaode.response.ResponseNotifyBody;
import com.hhly.ticket.service.ticket.dealer.gaode.response.Ticket;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.DateUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 高德
 * @author jiangwei
 * @date 2017年8月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class GaoDeDealer extends AbstractDealer implements INotify {

	public static final String SUCCESS = "0";

	public GaoDeDealer(ChannelBO bo, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(bo.getDrawerAccount());
		dealerInfo.setAccountPassword(DESUtil.decrypt(bo.getAccountPassword()));
		dealerInfo.setSendUrl(bo.getSendUrl());
		dealerInfo.setSearchMaxTicket(bo.getSearchMaxTicket() > 100 ? 100 : bo.getSearchMaxTicket());
		dealerInfo.setSearchAuto(bo.getSearchAuto());
		dealerInfo.setPreBatch(bo.getPreBatch());
		super.dealerInfo = dealerInfo;

	}

	/**
	 * 初始化 INotify 构造器
	 * 
	 * @param orderService
	 * @param alarmInfoService
	 */
	public GaoDeDealer(String deawerAccount, String accountPassword, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(deawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> tickets) {
		if (CollectionUtils.isEmpty(tickets)) {
			return true;
		}
		CathecticRequestMsg msg = new CathecticRequestMsg();
		TicketBO bo = tickets.get(0);
		String transactionType = getTransactionType(bo.getLotteryCode());
		CathecticBody body = getBody(transactionType, tickets);
		msg.setHeader(getHeader(transactionType, body));
		msg.setBody(body);
		String result = responseDealer(msg);
		CathecticResponseMsg resultMsg = getResponseMsg(CathecticResponseMsg.class, result);
		String code = resultMsg.getBody().getResponseCode();
		if (SUCCESS.equals(code)) {
			sendTicketSuccess(tickets, resultMsg.getBody().getResults().getTickets());
			return true;
		} else {
			sendTicketFail(tickets, code);
			return false;
		}
	}

	@Override
	protected String getRemark(String code) {
		return code + ":" + GaoDeUtil.getErrorMessage(code);
	}

	/**
	 * 请求送票成功
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午11:54:40
	 * @param tickets
	 * @param CathecticTicket
	 */
	private void sendTicketSuccess(List<TicketBO> tickets, List<Ticket> CathecticTicket) {
		for (TicketBO ticketBO : tickets) {
			for (Ticket ticket : CathecticTicket) {
				if (ticketBO.getBatchNum().equals(ticket.getTicketId())) {
					String code = ticket.getTicketResultCode();
					// 判断出票商受理票是否成功
					if (SUCCESS.equals(code)) {
						ticketBO.setThirdNum(ticket.getOrderID());
						doTicketSuccess(ticketBO);
					} else {
						doTicketFail(ticketBO, code);
					}
					break;
				}
			}
		}
	}

	/**
	 * 送票 获取请求body
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午11:29:30
	 * @param transactionType
	 * @param ticket
	 * @return
	 */
	private CathecticBody getBody(String transactionType, List<TicketBO> tickets) {
		CathecticBody body = null;
		TicketBO first = tickets.get(0);
		if ("P002".equals(transactionType)) {
			body = new CathecticBody();
		}else{
			BjdcCathecticBody cathecticBody = new BjdcCathecticBody();
			cathecticBody.setIssue(first.getChannelLotteryIssue());
			body = cathecticBody;
		}
		body.setLotteryId(first.getChannelLotteryCode());
		body.setRule(first.getChannelPlayType());
		List<CathecticTicket> cathecticTickets = new ArrayList<>();
		for (TicketBO ticket : tickets) {
			ticket.setBatchNum(dealerInfo.getPreBatch() + TicketUtil.getOrderNo());
			ticket.setBatchNumSeq("1");
			CathecticTicket t = new CathecticTicket();
			t.setBetContents(ticket.getChannelTicketContent());
			t.setChips(String.valueOf(ticket.getChips()));
			t.setMoney(String.valueOf((int) ticket.getTicketMoney()));
			t.setMultiple(String.valueOf(ticket.getMultipleNum()));
			t.setPassMode(ticket.getChannelPassMode());
			t.setTicketId(ticket.getBatchNum());
			t.setType(ticket.getChannelContentType());
			cathecticTickets.add(t);
		}
		body.setTickets(cathecticTickets);
		return body;
	}

	/**
	 * 获取彩种投注请求编码
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午11:27:45
	 * @param lotteryCode
	 * @return
	 */
	private String getTransactionType(int lotteryCode) {
		if (lotteryCode == 306 || lotteryCode == 307) {
			return "P001";// 北单
		} else if (lotteryCode == 300 || lotteryCode == 301) {
			return "P002";// 竞彩
		}
		return "P006";// 老足彩
	}

	@Override
	public double getDealerBalance() {
		BalanceRequestMsg msg = new BalanceRequestMsg();
		BalanceBody body = new BalanceBody();
		msg.setBody(body);
		msg.setHeader(getHeader("P005", body));
		String result = responseDealer(msg);
		BalanceResponseMsg responseMsg = getResponseMsg(BalanceResponseMsg.class, result);
		String allBalance = responseMsg.getBody().getAccount();
		if (StringUtils.isEmpty(allBalance)) {
			return 0.0;
		}
		return Double.parseDouble(allBalance);
	}

	@Override
	public String notifyOutTicket(String msg) {
		CheckResponseMsg responseMsg = getResponseMsg(CheckResponseMsg.class, msg);
		responseMsg.getBody().setResponseCode(SUCCESS);
		handleCheckTicket(responseMsg);
		return getSuccessReuslt("0");
	}

	/**
	 * @see code[0]:返回响应编码
	 */
	@Override
	public String getSuccessReuslt(String... code) {
		ResponseNotifyMsg msg = new ResponseNotifyMsg();
		ResponseNotifyBody body = new ResponseNotifyBody(code[0]);
		msg.setBody(body);
		msg.setHeader(getHeader(null, body));
		return msg.toXml();
	}

	/**
	 * 送票成功后默认检票时间（6分钟）
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 下午2:48:14
	 * @return
	 */
	@Override
	protected int getFirstCheckTime() {
		return 360_000;
	}

	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		String transactionType = getCheckTransactionType(list.get(0).getLotteryCode());
		return sendCheck(transactionType, list);
	}

	/**
	 * 发送检票查询
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 下午3:25:22
	 * @param transactionType
	 * @param sub
	 * @return
	 */
	private CheckResponseMsg sendCheck(String transactionType, List<DealerCheckBO> sub) {
		CheckRequestMsg msg = new CheckRequestMsg();
		CheckBody body = new CheckBody();
		msg.setBody(body);
		body.setTickets(new CheckTicket());
		List<String> list = new ArrayList<>();
		for (DealerCheckBO bo : sub) {
			list.add(bo.getBatchNum());
		}
		body.getTickets().setTickets(list);
		msg.setHeader(getHeader(transactionType, body));
		String result = responseDealer(msg);
		return getResponseMsg(CheckResponseMsg.class, result);
	}
	
    /**
     * 处理出票商返回赔率
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月10日 下午6:11:28
     * @param odd
     * @param channelContent
     * @return
     */
	@Override
	protected String doMatchOdd(String odd, String channelContent) {
		StringBuffer sb = new StringBuffer();
		StringBuffer tg = new StringBuffer();
		// 1001:3_1/1002:31_1 1001_1@1:3=3.10/1002_1@0:3=2.34,1=3.14
		// 1001:3/1002:31 1001@1:3=3.10/1002@0:3=2.34,1=3.14
		List<MatchInfo> mi = getMatchInfo(channelContent);
		Map<String, MatchOdds> moi = getMatchOddInfo(odd);
		for (MatchInfo matchInfo : mi) {
			char[] play = matchInfo.getPlay().toCharArray();
			// 判断投注内容是几位
			String test = matchInfo.getNumber() + "_" + play[0];
			int num = 2;
			if (moi.containsKey(test)) {
				num = 1;
			}
			if (sb.length() > 0) {
				sb.append("-");
			}
			for (int i = 0; i < play.length; i += num) {
				String key = matchInfo.getNumber() + "_" + play[i];
				if (num == 2) {
					key += play[i + 1];
				}
				if (i > 0) {
					sb.append("A");
				}
				MatchOdds matchOdds = moi.get(key);
				sb.append(matchOdds.getOdd());
				if(tg.length() > 0 ){
					tg.append("B");
				}
				if(StringUtils.isEmpty(matchOdds.getTarget())){
					tg.append("#");
				}else{
					tg.append(matchOdds.getTarget());	
				}
			}
		}
		return sb.append("@").append(tg).toString();
	}

	/**
	 * 处理出票商返回赔率
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 下午5:42:07
	 * @param odd
	 * @return key:场次编号（混合投注+玩法）+投注内容 普通 ：1001_3 混合：1001_1_3 value:MatchOdds
	 */
	@Override
	protected Map<String, MatchOdds> getMatchOddInfo(String odd) {
		Map<String, MatchOdds> map = new HashMap<>();
		String[] matchs = odd.split("/");
		for (String match : matchs) {
			String[] str = match.split("[:,]");
			String[] no = str[0].split("@");
			for (int i = 1; i < str.length; i++) {
				MatchOdds matchOdds = new MatchOdds();
				matchOdds.setTarget(no[1]);
				String[] py = str[i].split("=");
				matchOdds.setOdd(py[1]);
				map.put(no[0] + "_" + py[0],matchOdds);
			}
		}
		return map;
	}

	/**
	 * 解析送票格式
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 下午5:44:41
	 * @param channelContent
	 * @return
	 */
	@Override
	protected List<MatchInfo> getMatchInfo(String channelContent) {
		List<MatchInfo> result = new ArrayList<>();
		String[] matchs = channelContent.split("/");
		for (String match : matchs) {
			String[] str = match.split("[:_]");
			String m = str[0];
			if (str.length == 3) {
				m = m + "_" + str[2];
			}
			MatchInfo mi = new MatchInfo(m, str[1]);
			result.add(mi);
		}
		return result;
	}

	/**
	 * 获取检票查询编码
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 下午3:23:48
	 * @param lotteryCode
	 * @return
	 */
	private String getCheckTransactionType(int lotteryCode) {
		if (lotteryCode == 306 || lotteryCode == 307) {
			return "P003";// 北单
		} else if (lotteryCode == 300 || lotteryCode == 301) {
			return "P004";// 竞彩
		}
		return "P007";// 老足彩
	}

	/**
	 * 解析出票商返回结果 验证出票商返回加密是否正确
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午10:05:19
	 * @param cla
	 * @param xml
	 * @return
	 */
	protected <T extends AbstractMsg> T getResponseMsg(Class<T> cla, String xml) {
		String md5;
		T t;
		try {
			t = cla.newInstance().fromXML(xml);
			int start = xml.indexOf("<body>");
			int end = xml.indexOf("</body>") + 7;
			String body = xml.substring(start, end);
			md5 = getMd5(body);
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
	 * 获取头
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午10:04:56
	 * @param transactionType
	 * @param body
	 * @return
	 */
	protected <T extends IXml> Header getHeader(String transactionType, T body) {
		Header header = new Header();
		header.setUserName(dealerInfo.getDeawerAccount());
		header.setTransactionType(transactionType);
		header.setTimestamp(DateUtil.getNow(DateUtil.DATE_FORMAT_NUM));
		header.setDigest(getMd5(body));
		return header;
	}

	/**
	 * 请求
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午10:04:50
	 * @param msg
	 * @return
	 */
	protected <T extends AbstractMsg> String responseDealer(T msg) {
		String msgStr = msg.toXml();
		String result = null;
		try {
			LOGGER.info("高德出票商请求" + msgStr);
			result = HttpUtil.doPostCharset(dealerInfo.getSendUrl(), "UTF-8", msgStr);
			LOGGER.info("高德出票商响应" + result);
		} catch (IOException | URISyntaxException e) {
			LOGGER.error("高德出票商请求异常", e);
			result = e.getMessage();
		}
		return result;
	}

	/**
	 * body字符串加密
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午10:04:36
	 * @param body
	 * @return
	 */
	protected String getMd5(String body) {
		String md5Str = dealerInfo.getDeawerAccount() + body + "^" + dealerInfo.getAccountPassword();
		return Md5Util.md5_32(md5Str, "UTF-8");
	}

	/**
	 * 加密
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午10:04:42
	 * @param body
	 * @return
	 */
	protected <T extends IXml> String getMd5(T body) {
		String bodyStr = body.toXml().replace("<?xml version=\"1.0\" ?>", "");
		return getMd5(bodyStr);
	}

	public static void main(String[] args) {
		GaoDeDealer gDealer = new GaoDeDealer("324", "3243", null);
		TicketBO bo = new TicketBO();
		ReceiptContent string = gDealer.getOdd(bo, "1001_1@1:3=3.10/1002_3@0:31=2.34,12=3.14");
		System.out.println(string.getReceiptContent());
	}
}
