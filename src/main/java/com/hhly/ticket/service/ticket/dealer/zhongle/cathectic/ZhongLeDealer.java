package com.hhly.ticket.service.ticket.dealer.zhongle.cathectic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.DealerInfo;
import com.hhly.ticket.service.entity.MatchInfo;
import com.hhly.ticket.service.entity.MatchOdds;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.IXml;
import com.hhly.ticket.service.ticket.dealer.AbstractDealer;
import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.zhongle.AbstractMsg;
import com.hhly.ticket.service.ticket.dealer.zhongle.BalanceRequestMsg;
import com.hhly.ticket.service.ticket.dealer.zhongle.BalanceResponseMsg;
import com.hhly.ticket.service.ticket.dealer.zhongle.CathecticRequestMsg;
import com.hhly.ticket.service.ticket.dealer.zhongle.CathecticResponseMsg;
import com.hhly.ticket.service.ticket.dealer.zhongle.Head;
import com.hhly.ticket.service.ticket.dealer.zhongle.SearchRequestMsg;
import com.hhly.ticket.service.ticket.dealer.zhongle.SearchResponseMsg;
import com.hhly.ticket.service.ticket.dealer.zhongle.ZhongLeUtil;
import com.hhly.ticket.service.ticket.dealer.zhongle.request.BalanceBody;
import com.hhly.ticket.service.ticket.dealer.zhongle.request.CathecticBody;
import com.hhly.ticket.service.ticket.dealer.zhongle.request.Order;
import com.hhly.ticket.service.ticket.dealer.zhongle.request.SearchBody;
import com.hhly.ticket.service.ticket.dealer.zhongle.response.Match;
import com.hhly.ticket.service.ticket.dealer.zhongle.response.OrderResponse;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 众乐出票商
 * @author jiangwei
 * @date 2018年6月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class ZhongLeDealer extends AbstractDealer {

	public static final String SUCCESS = "0";

	public ZhongLeDealer(ChannelBO bo, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(bo.getDrawerAccount());
		dealerInfo.setAccountPassword(DESUtil.decrypt(bo.getAccountPassword()));
		dealerInfo.setSendUrl(bo.getSendUrl());
		dealerInfo.setSearchMaxTicket(bo.getSearchMaxTicket() > 50 ? 50 : bo.getSearchMaxTicket());
		dealerInfo.setSearchAuto(bo.getSearchAuto());
		dealerInfo.setPreBatch(bo.getPreBatch());
		super.dealerInfo = dealerInfo;
	}

	public ZhongLeDealer(String drawerAccount, String accountPassword, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(drawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> tickets) {
		CathecticBody body = getBody(tickets);
		CathecticRequestMsg request = new CathecticRequestMsg();
		request.setBody(body);
		String transactionType = "1001";
		request.setHead(getHead(transactionType, body));
		String result = responseDealer(request);
		CathecticResponseMsg response = getResponseMsg(CathecticResponseMsg.class, result);
		String code = response.getHead().getCode();
		if (SUCCESS.equals(code)) {
			sendTicketSuccess(tickets, response.getBody().getOrder());
			return true;
		} else {
			sendTicketFail(tickets, code);
			return false;
		}
	}

	/**
	 * 送票成功
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年6月21日 下午4:29:06
	 * @param tickets
	 * @param reponseTicket
	 */
	private void sendTicketSuccess(List<TicketBO> tickets, List<OrderResponse> reponseTicket) {
		for (TicketBO ticketBO : tickets) {
			for (OrderResponse order : reponseTicket) {
				if (ticketBO.getBatchNum().equals(order.getId())) {
					String code = order.getCode();
					// 判断出票商受理票是否成功
					if (SUCCESS.equals(code)) {
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
	 * 封装投注消息体
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年6月21日 下午4:29:28
	 * @param ticket
	 * @return
	 */
	private CathecticBody getBody(List<TicketBO> ticket) {
		List<Order> orders = new ArrayList<>();
		for (TicketBO ticketBO : ticket) {
			Order order = new Order();
			order.setId(dealerInfo.getPreBatch() + TicketUtil.getOrderNo());
			order.setLotID(ticketBO.getChannelLotteryCode());
			order.setLotIssue(ticketBO.getChannelLotteryIssue());
			order.setChipMoney(String.valueOf((int) ticketBO.getTicketMoney()));
			order.setChipCode(ticketBO.getChannelTicketContent());
			order.setChipMulti(ticketBO.getMultipleNum().toString());
			if (ticketBO.getLottoAdd() == 1) {
				order.setOneMoney("3");
			} else {
				order.setOneMoney("2");
			}
			orders.add(order);
			ticketBO.setBatchNum(order.getId());
			ticketBO.setBatchNumSeq("1");
		}
		CathecticBody body = new CathecticBody();
		body.setOrder(orders);
		return body;
	}

	@Override
	public double getDealerBalance() {
		BalanceRequestMsg msg = new BalanceRequestMsg();
		BalanceBody body = new BalanceBody();
		msg.setBody(body);
		String transactionType = "2010";
		msg.setHead(getHead(transactionType, body));
		String result = responseDealer(msg);
		BalanceResponseMsg brm = getResponseMsg(BalanceResponseMsg.class, result);
		return Double.valueOf(brm.getBody().getAccBalance());
	}
	
	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		SearchRequestMsg requestMsg = new SearchRequestMsg();
		SearchBody body = new SearchBody();
		List<String> orders  = new ArrayList<>();
		body.setOrders(orders);
		for (DealerCheckBO bo : list) {
			orders.add(bo.getBatchNum());
		}
		requestMsg.setBody(body);
		requestMsg.setHead(getHead("2001", body));
		String result = responseDealer(requestMsg);
		ICheckResponse msg = getResponseMsg(SearchResponseMsg.class, result);
		return msg;
	}
	
	@Override
	protected String getRemark(String code) {
		return code + ":" + ZhongLeUtil.getErrorMessage(code);
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
		T t;
		try {
			t = cla.newInstance().fromXML(xml);
		} catch (Exception e) {
			throw new ServiceRuntimeException("xml格式转换错误" + xml, e);
		}
		return t;
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
	protected <T extends IXml> Head getHead(String transactionType, T body) {
		Head head = new Head();
		head.setAgentid(dealerInfo.getDeawerAccount());
		head.setTranscode(transactionType);
		head.setMsgid(TicketUtil.getOnlyNo());
		head.setCipher(getMd5(head, body));
		return head;
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
			LOGGER.info("众乐出票商请求" + msgStr);
			result = HttpUtil.doPostCharset(dealerInfo.getSendUrl(), "UTF-8", msgStr);
			LOGGER.info("众乐出票商响应" + result);
		} catch (Exception e) {
			LOGGER.error("众乐出票商请求异常", e);
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
	protected String getMd5(Head head, IXml body) {
		String bodyStr = body.toXml().replace("<?xml version=\"1.0\" ?>", "").replace("<body>", "").replace("</body>",
				"");
		return getMd5(head, bodyStr);
	}

	protected String getMd5(Head head, String body) {
		StringBuilder sb = new StringBuilder(dealerInfo.getDeawerAccount());
		sb.append(head.getTranscode());
		sb.append(head.getMsgid());
		sb.append(body);
		sb.append(dealerInfo.getAccountPassword());
		return Md5Util.md5_32(sb.toString(), "UTF-8").toLowerCase();
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
	@Override
	protected String doMatchOdd(String odd, String channelContent) {
		return doMatchOddSport(odd, channelContent);
	}

	/**
	 * 处理出票商返回赔率
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 下午5:42:07
	 * @param odd
	 * @return key:场次编号（混合投注+玩法）+投注内容 普通 ：1001_3 混合：1001_1_3 value:赔率
	 */
	@Override
	protected Map<String, MatchOdds> getMatchOddInfo(String odd) {
		List<Match> matchs = JsonUtil.json2ObjectList(odd, Match.class);
		Map<String, MatchOdds> map = new HashMap<>();
		for (Match match : matchs) {
			String[] str = StringUtils.tokenizeToStringArray(match.getValue(), "=|/");
			for (int i = 0; i < str.length; i+=2) {
				MatchOdds mo = new MatchOdds();
				mo.setOdd(str[i+1]);
				mo.setTarget(match.getRq());
				map.put(match.getId() + "_" + str[i], mo);
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
		// ZQHH|CBF>180523001=2:0/1:3,CBF>180523002=2:0/0:3,RSPF>180523003=3,SPF>180523004=1/0|4*1
		// SPF|100608001=3/0,100608002=3/1,100608003=0|3*1
		List<MatchInfo> result = new ArrayList<>();
		String[] cts = StringUtils.tokenizeToStringArray(channelContent, "|,");
		for (int i = 1; i < cts.length - 1; i++) {
			String[] ms = StringUtils.tokenizeToStringArray(cts[i], ">=");
			String match = ms[0];
			String content = ms[1];
			if (ms.length == 3) {
				match = ms[1];
				content = ms[2];
			}
			String[] choose = StringUtils.tokenizeToStringArray(content, "/");
			StringBuilder sb = new StringBuilder();
			for (String s : choose) {
				if (sb.length() > 0) {
					sb.append("/");
				}
				sb.append(s);
			}
			MatchInfo mi = new MatchInfo(match, sb.toString());
			result.add(mi);
		}
		return result;
	}
	
	

}
