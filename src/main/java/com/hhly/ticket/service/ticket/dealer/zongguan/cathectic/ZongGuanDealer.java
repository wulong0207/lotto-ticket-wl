package com.hhly.ticket.service.ticket.dealer.zongguan.cathectic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.DealerInfo;
import com.hhly.ticket.service.entity.MatchInfo;
import com.hhly.ticket.service.entity.MatchOdds;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.AbstractDealer;
import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.INotify;
import com.hhly.ticket.service.ticket.dealer.zongguan.AbstractMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.BalanceRequestMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.BalanceResponseMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.CathecticRequestMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.CathecticResponseMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.CheckRequestMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.CheckResponseMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.NotifyTicketRequestMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.NotifyTicketResponseMsg;
import com.hhly.ticket.service.ticket.dealer.zongguan.ZongGuanUtil;
import com.hhly.ticket.service.ticket.dealer.zongguan.request.NotifyResultTicket;
import com.hhly.ticket.service.ticket.dealer.zongguan.request.QueryTicket;
import com.hhly.ticket.service.ticket.dealer.zongguan.request.RequestTicket;
import com.hhly.ticket.service.ticket.dealer.zongguan.request.TicketOrder;
import com.hhly.ticket.service.ticket.dealer.zongguan.response.CheckTicket;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 广州纵贯
 * @author jiangwei
 * @date 2018年3月13日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class ZongGuanDealer extends AbstractDealer implements INotify {

	private static final String SUCCESS = "000";

	private static final Map<String, String> CONTENT = new HashMap<>();

	static {
		CONTENT.put("胜胜", "胜-胜");
		CONTENT.put("胜平", "胜-平");
		CONTENT.put("胜负", "胜-负");
		CONTENT.put("平胜", "平-胜");
		CONTENT.put("平平", "平-平");
		CONTENT.put("平负", "平-负");
		CONTENT.put("负胜", "负-胜");
		CONTENT.put("负平", "负-平");
		CONTENT.put("负负", "负-负");

		CONTENT.put("胜1-5分", "胜1-5");
		CONTENT.put("胜6-10分", "胜6-10");
		CONTENT.put("胜11-15分", "胜11-15");
		CONTENT.put("胜16-20分", "胜16-20");
		CONTENT.put("胜21-25分", "胜21-25");
		CONTENT.put("胜26分以上", "胜26+");
		CONTENT.put("负1-5分", "负1-5");
		CONTENT.put("负6-10分", "负6-10");
		CONTENT.put("负11-15分", "负11-15");
		CONTENT.put("负16-20分", "负16-20");
		CONTENT.put("负21-25分", "负21-25");
		CONTENT.put("负26分以上", "负26+");
	}

	public ZongGuanDealer(ChannelBO bo, IOrderService orderService) {
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

	public ZongGuanDealer(String deawerAccount, String accountPassword, IOrderService orderService) {
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
		CathecticRequestMsg requestMsg = getCathecticBody(tickets);
		String result = responseDealer(requestMsg);
		CathecticResponseMsg responseMsg = getResponseMsg(CathecticResponseMsg.class, result);
		String transcode = responseMsg.getHead().getTranscode();
		if ("102".equals(transcode)) {
			sendTicketSuccess(tickets, responseMsg.getBody().getTickets());
			return true;
		} else {
			sendTicketFail(tickets, transcode);
		}
		return false;
	}

	/**
	 * 出票成功解析
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月16日 上午10:41:16
	 * @param tickets
	 * @param cts
	 */
	private void sendTicketSuccess(List<TicketBO> tickets,
			List<com.hhly.ticket.service.ticket.dealer.zongguan.response.ResponseTicket> cts) {
		for (TicketBO ticketBO : tickets) {
			for (com.hhly.ticket.service.ticket.dealer.zongguan.response.ResponseTicket t : cts) {
				if (ticketBO.getBatchNum().equals(t.getTicketId())) {
					String code = t.getStatusCode();
					// 判断出票商受理票是否成功
					if (SUCCESS.equals(code)) {
						ticketBO.setThirdNum(t.getPalmid());
						doTicketSuccess(ticketBO);
					} else {
						doTicketFail(ticketBO, code);
						ticketBO.setChannelRemark(ticketBO.getChannelRemark() + "|" + t.getDetailmessage());
					}
					break;
				}
			}
		}
	}

	@Override
	protected String getRemark(String code) {
		return ZongGuanUtil.getErrorMessage(code);
	}

	/**
	 * 封装投注请求内容
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月16日 上午10:02:45
	 * @param tickets
	 * @return
	 */
	private CathecticRequestMsg getCathecticBody(List<TicketBO> tickets) {
		CathecticRequestMsg requestMsg = new CathecticRequestMsg("002", dealerInfo.getDeawerAccount());
		TicketBO bo = tickets.get(0);
		TicketOrder ticketorder = requestMsg.getBody().getTicketorder();
		ticketorder.setLotteryId(bo.getChannelLotteryCode());
		ticketorder.setTicketsnum(String.valueOf(tickets.size()));
		List<RequestTicket> cts = ticketorder.getTickets();
		int money = 0;
		for (TicketBO ticketBO : tickets) {
			ticketBO.setBatchNum(dealerInfo.getPreBatch() + TicketUtil.getOrderNo());
			ticketBO.setBatchNumSeq("1");
			money = money + (int) ticketBO.getTicketMoney();
			RequestTicket ticket = new RequestTicket();
			ticket.setTicketId(ticketBO.getBatchNum());
			ticket.setBetType(ticketBO.getChannelPlayType());
			ticket.setIssueNumber(ticketBO.getChannelLotteryIssue());
			ticket.setBetUnits(String.valueOf(ticketBO.getChips()));
			ticket.setBetMoney(String.valueOf(ticketBO.getTicketMoney()));
			ticket.setIsAppend(String.valueOf(ticketBO.getLottoAdd()));
			ticket.setBetContent(ticketBO.getChannelTicketContent());
			ticket.setMultiple(ticketBO.getMultipleNum().toString());
			cts.add(ticket);
		}
		ticketorder.setTotalmoney(String.valueOf(money));
		return requestMsg;
	}

	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		CheckRequestMsg requestMsg = new CheckRequestMsg("003", dealerInfo.getDeawerAccount());
		List<QueryTicket> qt = requestMsg.getBody().getQueryTickets();
		for (DealerCheckBO bo : list) {
			qt.add(new QueryTicket(bo.getBatchNum()));
		}
		String result = responseDealer(requestMsg);
		ICheckResponse msg = getResponseMsg(CheckResponseMsg.class, result);
		return msg;
	}

	@Override
	protected String doMatchOdd(String odd, String channelContent) {
		return doMatchOddSport(odd, channelContent);
	}

	@Override
	protected Map<String, MatchOdds> getMatchOddInfo(String odd) {
		Map<String, MatchOdds> result = new HashMap<>();
		String[] matchs = odd.split("/");
		for (String match : matchs) {
			String[] str = match.split("\\[");
			String m = str[0].substring(0, str[0].length() - 1);
			String[] content = StringUtils.tokenizeToStringArray(str[1], "^]");
			for (String cn : content[0].split(",")) {
				String[] temp = cn.split("=");
				MatchOdds mo = new MatchOdds();
				mo.setOdd(temp[1]);
				if (content.length == 2) {
					mo.setTarget(content[1]);
				}
				String con = CONTENT.get(temp[0]);
				con = con == null ? temp[0] : con;
				result.put(m + "_" + con, mo);
			}
		}
		return result;
	}

	@Override
	protected List<MatchInfo> getMatchInfo(String channelContent) {
		List<MatchInfo> result = new ArrayList<>();
		String[] matchs = channelContent.split("/");
		for (String match : matchs) {
			String[] content = StringUtils.split(match, ":[");
			result.add(new MatchInfo(content[0], content[1].replace(",", "/").replace("]", "")));
		}
		return result;
	}

	@Override
	public double getDealerBalance() {
		BalanceRequestMsg msg = new BalanceRequestMsg("005", dealerInfo.getDeawerAccount());
		String result = responseDealer(msg);
		BalanceResponseMsg responseMsg = getResponseMsg(BalanceResponseMsg.class, result);
		return Double.parseDouble(responseMsg.getBody().getPartneraccount().getBalance());
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
		String rquestMsg = getRequetMsg(msg);
		String result = null;
		try {
			LOGGER.info("纵贯出票商请求" + rquestMsg);
			result = HttpUtil.doPostStream(dealerInfo.getSendUrl(), rquestMsg, "text/xml");
			LOGGER.info("纵贯出票商响应" + result);
		} catch (Exception e) {
			LOGGER.error("纵贯出票商请求异常", e);
			result = e.getMessage();
		}
		return result;
	}

	/**
	 * 获取请求字符串
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月20日 下午4:34:34
	 * @param msg
	 * @return
	 */
	private <T extends AbstractMsg> String getRequetMsg(T msg) {
		String msgStr = msg.toXml().replace("<?xml version=\"1.0\" ?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		StringBuilder sb = new StringBuilder();
		sb.append("transcode=");
		sb.append(msg.getHead().getTranscode());
		sb.append("&msg=");
		sb.append(msgStr);
		sb.append("&key=");
		sb.append(getMd5(msg.getHead().getTranscode(), msgStr));
		sb.append("&partnerid=");
		sb.append(dealerInfo.getDeawerAccount());
		return sb.toString();
	}

	/**
	 * 密码加密
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月13日 下午4:55:29
	 * @param body
	 * @return
	 */
	protected String getMd5(String transcode, String body) {
		String md5Str = transcode + body + dealerInfo.getAccountPassword();
		return Md5Util.md5_32(md5Str, "UTF-8").toUpperCase();
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
	protected <T extends AbstractMsg> T getResponseMsg(Class<T> cla, String result) {
		String[] strings = result.split("&");
		String msg = "", key = "", transcode = "";
		for (String str : strings) {
			if (str.startsWith("msg=")) {
				msg = str.replaceFirst("msg=", "");
			} else if (str.startsWith("key=")) {
				key = str.replaceFirst("key=", "");
			} else if (str.startsWith("transcode=")) {
				transcode = str.replaceFirst("transcode=", "");
			}
		}
		String md5;
		T t;
		try {
			t = cla.newInstance().fromXML(msg);
			md5 = getMd5(transcode, msg);
		} catch (Exception e) {
			throw new ServiceRuntimeException("xml格式转换错误" + msg, e);
		}
		if (Objects.equals(md5, key)) {
			return t;
		} else {
			throw new ServiceRuntimeException("加密验证错误：" + result);
		}
	}

	@Override
	public String notifyOutTicket(String result) {
		NotifyTicketResponseMsg msg = getResponseMsg(NotifyTicketResponseMsg.class, result);
		handleCheckTicket(msg);
		NotifyTicketRequestMsg reuslt = new NotifyTicketRequestMsg("107", dealerInfo.getDeawerAccount());
		List<NotifyResultTicket> list = reuslt.getBody().getReturnticketresults();
		for (CheckTicket tikcet : msg.getBody().getTicketresults()) {
			list.add(new NotifyResultTicket(tikcet.getBatchNum(), dealerInfo.getDeawerAccount()));
		}
		return getRequetMsg(reuslt);
	}

	@Override
	public String getSuccessReuslt(String... code) {
		return null;
	}
}
