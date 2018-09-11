package com.hhly.ticket.service.ticket.dealer.tengshun.cathectic;

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
import com.hhly.ticket.service.ticket.dealer.AbstractDealer;
import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.INotify;
import com.hhly.ticket.service.ticket.dealer.tengshun.AbstractMsg;
import com.hhly.ticket.service.ticket.dealer.tengshun.CathecticRequestMsg;
import com.hhly.ticket.service.ticket.dealer.tengshun.CathecticResponseMsg;
import com.hhly.ticket.service.ticket.dealer.tengshun.NotifyRequestMsg;
import com.hhly.ticket.service.ticket.dealer.tengshun.NotifyResponseMsg;
import com.hhly.ticket.service.ticket.dealer.tengshun.SearchRequestMsg;
import com.hhly.ticket.service.ticket.dealer.tengshun.SearchResponseMsg;
import com.hhly.ticket.service.ticket.dealer.tengshun.TengShunUtil;
import com.hhly.ticket.service.ticket.dealer.tengshun.request.Query;
import com.hhly.ticket.service.ticket.dealer.tengshun.request.RqCathecticBody;
import com.hhly.ticket.service.ticket.dealer.tengshun.request.RqTicket;
import com.hhly.ticket.service.ticket.dealer.tengshun.request.RqTickets;
import com.hhly.ticket.service.ticket.dealer.tengshun.request.RqUser;
import com.hhly.ticket.service.ticket.dealer.tengshun.response.NotifyTicket;
import com.hhly.ticket.service.ticket.dealer.tengshun.response.RpTicket;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 滕顺出票商
 * @author jiangwei
 * @date 2018年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class TengShunDealer extends AbstractDealer implements INotify {

	private static final Map<String, String> OPTION;

	static {
		OPTION = new HashMap<>();
		// 足球
		OPTION.put("SPF3", "胜");
		OPTION.put("SPF1", "平");
		OPTION.put("SPF0", "负");

		OPTION.put("RSPF3", "胜");
		OPTION.put("RSPF1", "平");
		OPTION.put("RSPF0", "负");

		OPTION.put("CBF9:0", "9:0");
		OPTION.put("CBF1:0", "1:0");
		OPTION.put("CBF2:0", "2:0");
		OPTION.put("CBF2:1", "2:1");
		OPTION.put("CBF3:0", "3:0");
		OPTION.put("CBF3:1", "3:1");
		OPTION.put("CBF3:2", "3:2");
		OPTION.put("CBF4:0", "4:0");
		OPTION.put("CBF4:1", "4:1");
		OPTION.put("CBF4:2", "4:2");
		OPTION.put("CBF5:0", "5:0");
		OPTION.put("CBF5:1", "5:1");
		OPTION.put("CBF5:2", "5:2");
		OPTION.put("CBF9:9", "9:9");
		OPTION.put("CBF0:0", "0:0");
		OPTION.put("CBF1:1", "1:1");
		OPTION.put("CBF2:2", "2:2");
		OPTION.put("CBF3:3", "3:3");
		OPTION.put("CBF0:9", "0:9");
		OPTION.put("CBF0:1", "0:1");
		OPTION.put("CBF0:2", "0:2");
		OPTION.put("CBF1:2", "1:2");
		OPTION.put("CBF0:3", "0:3");
		OPTION.put("CBF1:3", "1:3");
		OPTION.put("CBF2:3", "2:3");
		OPTION.put("CBF0:4", "0:4");
		OPTION.put("CBF1:4", "1:4");
		OPTION.put("CBF2:4", "2:4");
		OPTION.put("CBF0:5", "0:5");
		OPTION.put("CBF1:5", "1:5");
		OPTION.put("CBF2:5", "2:5");

		OPTION.put("BQC3-3", "胜胜");
		OPTION.put("BQC3-1", "胜平");
		OPTION.put("BQC3-0", "胜负");
		OPTION.put("BQC1-3", "平胜");
		OPTION.put("BQC1-1", "平平");
		OPTION.put("BQC1-0", "平负");
		OPTION.put("BQC0-3", "负胜");
		OPTION.put("BQC0-1", "负平");
		OPTION.put("BQC0-0", "负负");

		OPTION.put("JQS0", "0");
		OPTION.put("JQS1", "1");
		OPTION.put("JQS2", "2");
		OPTION.put("JQS3", "3");
		OPTION.put("JQS4", "4");
		OPTION.put("JQS5", "5");
		OPTION.put("JQS6", "6");
		OPTION.put("JQS7", "7");

		// 篮球
		OPTION.put("SF3", "胜");
		OPTION.put("SF0", "负");

		OPTION.put("RFSF3", "胜");
		OPTION.put("RFSF0", "负");

		OPTION.put("DXF3", "大");
		OPTION.put("DXF0", "小");

		OPTION.put("SFC01", "主1-5");
		OPTION.put("SFC02", "主6-10");
		OPTION.put("SFC03", "主11-15");
		OPTION.put("SFC04", "主16-20");
		OPTION.put("SFC05", "主21-25");
		OPTION.put("SFC06", "主26");
		OPTION.put("SFC11", "客1-5");
		OPTION.put("SFC12", "客6-10");
		OPTION.put("SFC13", "客11-15");
		OPTION.put("SFC14", "客16-20");
		OPTION.put("SFC15", "客21-25");
		OPTION.put("SFC16", "客26");

	}

	public TengShunDealer(ChannelBO bo, IOrderService orderService) {
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

	public TengShunDealer(String drawerAccount, String accountPassword, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(drawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> tickets) {
		CathecticRequestMsg msg = getCathecticRequest(tickets);
		String result = responseDealer(msg);
		CathecticResponseMsg crm = getResponseMsg(CathecticResponseMsg.class, result);
		if ("0".equals(crm.getResult().getCode())) {
			sendTicketSuccess(tickets, crm.getBody().getTickets().getTickets());
			return true;
		} else {
			sendTicketFail(tickets, crm.getResult().getCode());
			return false;
		}
	}

	/**
	 * 判断是否出票成功
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月18日 下午12:04:06
	 * @param tickets
	 * @param reponseTicket
	 */
	private void sendTicketSuccess(List<TicketBO> tickets, List<RpTicket> reponseTicket) {
		for (TicketBO ticketBO : tickets) {
			for (RpTicket ticket : reponseTicket) {
				if (ticketBO.getBatchNum().equals(ticket.getApply())) {
					String code = ticket.getCode();
					// 判断出票商受理票是否成功
					if ("0".equals(code)) {
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
	 * 封装提交出票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月18日 上午11:58:23
	 * @param ticket
	 * @return
	 */
	private CathecticRequestMsg getCathecticRequest(List<TicketBO> ticket) {
		TicketBO bo = ticket.get(0);
		int lotteryCode = bo.getLotteryCode();
		String sid = "";
		if (lotteryCode == 300) {
			sid = "10002";
		} else if (lotteryCode == 301) {
			sid = "10003";
		} else if (lotteryCode == 306) {
			sid = "10005";
		} else if (lotteryCode == 308 || lotteryCode == 309) {
			sid = "10006";
		} else {
			sid = "10001";
		}
		CathecticRequestMsg msg = new CathecticRequestMsg(sid, dealerInfo.getDeawerAccount());
		RqCathecticBody body = new RqCathecticBody();
		RqUser user = new RqUser("", "", "");
		RqTickets tickets = new RqTickets();
		tickets.setGid(bo.getChannelLotteryCode());
		tickets.setPid(bo.getChannelLotteryIssue());
		List<RqTicket> rqTickets = new ArrayList<>();
		for (TicketBO tBo : ticket) {
			RqTicket rt = new RqTicket();
			rt.setApply(dealerInfo.getPreBatch() + TicketUtil.getOrderNo());
			rt.setCodes(tBo.getChannelTicketContent());
			rt.setMoney((int) tBo.getTicketMoney() + "");
			rt.setMulity(tBo.getMultipleNum().toString());
			rqTickets.add(rt);
			tBo.setBatchNum(rt.getApply());
			tBo.setBatchNumSeq("1");
		}
		msg.setBody(body);
		body.setUser(user);
		body.setTickets(tickets);
		tickets.setTickets(rqTickets);
		return msg;
	}

	@Override
	public double getDealerBalance() {
		SearchRequestMsg msg = new SearchRequestMsg("20010", dealerInfo.getDeawerAccount());
		String result = responseDealer(msg);
		SearchResponseMsg brm = getResponseMsg(SearchResponseMsg.class, result);
		return Double.parseDouble(brm.getBody().getRows().getRows().get(0).getBalance());
	}

	/**
	 * 请求
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月18日 上午10:26:55
	 * @param msg
	 * @return
	 */
	protected <T extends AbstractMsg> String responseDealer(T msg) {
		String rquestMsg = getRequetMsg(msg);
		String result = null;
		try {
			LOGGER.info("滕顺出票商请求" + rquestMsg);
			result = HttpUtil.doPostCharset(dealerInfo.getSendUrl(), "UTF-8", rquestMsg);
			LOGGER.info("滕顺出票商响应" + result);
		} catch (Exception e) {
			LOGGER.error("滕顺出票商请求异常", e);
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
		if ("10002".equals(msg.getHead().getSid()) || "10003".equals(msg.getHead().getSid())) {
			msgStr = msgStr.replaceAll("&gt;", ">");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("xml=");
		sb.append(msgStr);
		sb.append("&sign=");
		sb.append(getMd5(msgStr, dealerInfo.getAccountPassword()));
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
	protected String getMd5(String body, String key) {
		return Md5Util.md5_32(body + key, "UTF-8");
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
		T t;
		try {
			t = cla.newInstance().fromXML(result);
			return t;
		} catch (Exception e) {
			throw new ServiceRuntimeException("xml格式转换错误" + result, e);
		}
	}

	@Override
	protected String getRemark(String code) {
		return TengShunUtil.getErrorMessage(code);
	}

	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		SearchRequestMsg requestMsg = getCheckMsg(list);
		String result = responseDealer(requestMsg);
		ICheckResponse msg = getResponseMsg(SearchResponseMsg.class, result);
		return msg;
	}

	/**
	 * 封装检票接口请求
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月18日 下午3:05:19
	 * @param list
	 * @return
	 */
	private SearchRequestMsg getCheckMsg(List<DealerCheckBO> list) {
		String gid = "";
		StringBuilder sb = new StringBuilder();
		for (DealerCheckBO dealerCheckBO : list) {
			if (sb.length() > 0) {
				sb.append(",");
			} else {
				gid = TengShunUtil.getChannelLotteryCode(dealerCheckBO.getLotteryCode());
			}
			sb.append(dealerCheckBO.getBatchNum());
		}
		SearchRequestMsg msg = new SearchRequestMsg("20008", dealerInfo.getDeawerAccount());
		Query query = msg.getBody().getQuery();
		query.setGid(gid);
		query.setApply(sb.toString());
		return msg;
	}

	@Override
	public String notifyOutTicket(String result) {
		if (StringUtils.isEmpty(result)) {
			return "";
		}
		ICheckResponse msg = getResponseMsg(NotifyRequestMsg.class, result);
		handleCheckTicket(msg);

		NotifyResponseMsg nr = new NotifyResponseMsg();
		List<NotifyTicket> rt = new ArrayList<>();
		nr.setTickets(rt);

		@SuppressWarnings("unchecked")
		List<NotifyTicket> tickets = (List<NotifyTicket>) msg.getTicket();
		for (NotifyTicket notifyTicket : tickets) {
			NotifyTicket nt = new NotifyTicket();
			nt.setGid(notifyTicket.getGid());
			nt.setPid(notifyTicket.getPid());
			nt.setBid(notifyTicket.getBid());
			nt.setApply(notifyTicket.getApply());
			nt.setCode("0");
			nt.setDesc("成功");
			rt.add(nt);
		}
		return nr.toXml();
	}

	@Override
	public String getSuccessReuslt(String... code) {
		return null;
	}

	/**
	 * 查询当前期
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月19日 上午9:31:27
	 * @param code
	 */
	public void searchIssue(String code) {
		SearchRequestMsg msg = new SearchRequestMsg("20000", dealerInfo.getDeawerAccount());
		msg.getBody().getQuery().setGid(code);
		String result = responseDealer(msg);
		System.out.println(result);
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
		// 180523001(主-1)=负@1.45元,180523002=负@6.10元

		// 180523001=(2:0)@9.50元+(1:3)@24.00元,180523002=(2:0)@7.25元+(0:3)@80.00元,180523003(主-1)=胜@2.01元,180523004=平@4.35元+负@6.30元
		Map<String, MatchOdds> map = new HashMap<>();
		String[] odds = odd.split(",");
		for (String str : odds) {
			str = str.replaceAll("26\\+", "26");
			String[] xx = StringUtils.tokenizeToStringArray(str, "=+");
			String match = xx[0];
			String target = null;
			if (match.indexOf("(") != -1) {
				String[] tg = StringUtils.tokenizeToStringArray(match, "()");
				match = tg[0];
				target = tg[1].replace("主", "");
			}
			for (int i = 1; i < xx.length; i++) {
				String[] ct = StringUtils.tokenizeToStringArray(xx[i], "()@元");
				MatchOdds mo = new MatchOdds();
				mo.setOdd(ct[1]);
				mo.setTarget(target);
				map.put(match + "_" + ct[0], mo);
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
		// ZQHH|180523001>CBF=2:0/1:3,180523002>CBF=2:0/0:3,180523003>RSPF=3,180523004>SPF=1/0|4*1
		// ZQHH|180523001>RSPF=0,180523002>SPF=0|2*1
		// SPF|100608001=3/0,100608002=3/1,100608003=0|3*1
		List<MatchInfo> result = new ArrayList<>();
		String[] cts = StringUtils.tokenizeToStringArray(channelContent, "|,");
		String play = cts[0];
		for (int i = 1; i < cts.length - 1; i++) {
			String[] ms = StringUtils.tokenizeToStringArray(cts[i], ">=");
			String match = ms[0];
			String content = ms[1];
			if (ms.length == 3) {
				play = ms[1];
				content = ms[2];
			}
			String[] choose = StringUtils.tokenizeToStringArray(content, "/");
			StringBuilder sb = new StringBuilder();
			for (String s : choose) {
				if (sb.length() > 0) {
					sb.append("/");
				}
				sb.append(OPTION.get(play + s));
			}
			MatchInfo mi = new MatchInfo(match, sb.toString());
			result.add(mi);
		}
		return result;
	}

}
