package com.hhly.ticket.service.ticket.dealer.shenfu.cathectic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.hhly.ticket.base.exception.ResponseFaillRuntimeException;
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
import com.hhly.ticket.service.ticket.dealer.shenfu.AbstractXml;
import com.hhly.ticket.service.ticket.dealer.shenfu.BalanceRequestMsg;
import com.hhly.ticket.service.ticket.dealer.shenfu.BalanceResponseMsg;
import com.hhly.ticket.service.ticket.dealer.shenfu.CathecticRequestMsg;
import com.hhly.ticket.service.ticket.dealer.shenfu.CathecticResponseMsg;
import com.hhly.ticket.service.ticket.dealer.shenfu.Content;
import com.hhly.ticket.service.ticket.dealer.shenfu.Head;
import com.hhly.ticket.service.ticket.dealer.shenfu.SearchRequestMsg;
import com.hhly.ticket.service.ticket.dealer.shenfu.SearchResponseMsg;
import com.hhly.ticket.service.ticket.dealer.shenfu.ShenFuUtil;
import com.hhly.ticket.service.ticket.dealer.shenfu.request.BalanceContent;
import com.hhly.ticket.service.ticket.dealer.shenfu.request.RequestOrder;
import com.hhly.ticket.service.ticket.dealer.shenfu.request.SearchOrder;
import com.hhly.ticket.service.ticket.dealer.shenfu.response.ResponseOrder;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.DateUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 神福
 * @author jiangwei
 * @date 2018年6月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class ShenFuDealer extends AbstractDealer {

	public static final String SUCCESS = "0";

	public ShenFuDealer(ChannelBO bo, IOrderService orderService) {
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

	public ShenFuDealer(String drawerAccount, String accountPassword, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(drawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> tickets) {
		CathecticRequestMsg msg = new CathecticRequestMsg();
		List<RequestOrder> orderlist = new ArrayList<>();
		msg.setOrderlist(orderlist);
		for (TicketBO bo : tickets) {
			RequestOrder order = new RequestOrder();
			order.setLotterytype(bo.getChannelLotteryCode());
			order.setPhase(bo.getChannelLotteryIssue());
			order.setOrderid(dealerInfo.getPreBatch() + TicketUtil.getOrderNo());
			order.setPlaytype(bo.getChannelPlayType());
			order.setBetcode(bo.getChannelTicketContent());
			order.setMultiple(bo.getMultipleNum().toString());
			order.setAmount(String.valueOf((int) bo.getTicketMoney()));
			order.setAdd(String.valueOf(bo.getLottoAdd()));
			order.setEndtime(DateUtil.convertDateToStr(bo.getEndTicketTime()));
			bo.setBatchNum(order.getOrderid());
			bo.setBatchNumSeq("1");
			orderlist.add(order);
		}
		try {
			CathecticResponseMsg brm = getBody(CathecticResponseMsg.class, getContent("801", msg));
			sendTicketSuccess(tickets, brm.getOrderlist());
			return true;
		} catch (ResponseFaillRuntimeException e) {
			sendTicketFail(tickets, e.getCode());
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
	private void sendTicketSuccess(List<TicketBO> tickets, List<ResponseOrder> orderlist) {
		for (TicketBO ticketBO : tickets) {
			for (ResponseOrder order : orderlist) {
				if (ticketBO.getBatchNum().equals(order.getOrderid())) {
					String code = order.getErrorcode();
					// 判断出票商受理票是否成功
					if (SUCCESS.equals(code)) {
						ticketBO.setThirdNum(order.getSysid());
						doTicketSuccess(ticketBO);
					} else {
						doTicketFail(ticketBO, code);
					}
					break;
				}
			}
		}
	}

	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		SearchRequestMsg requestMsg = new SearchRequestMsg();
		List<SearchOrder> orderlist = new ArrayList<>();
		requestMsg.setOrderlist(orderlist);
		for (DealerCheckBO bo : list) {
			SearchOrder order = new SearchOrder();
			order.setOrderid(bo.getBatchNum());
			orderlist.add(order);
		}
		ICheckResponse msg;
		try {
			SearchResponseMsg responseMsg = getBody(SearchResponseMsg.class, getContent("802", requestMsg));
			responseMsg.setCode(SUCCESS);
			msg = responseMsg;
		} catch (ResponseFaillRuntimeException e) {
			SearchResponseMsg srm = new SearchResponseMsg();
			srm.setCode(e.getCode());
			srm.setMessage(e.getMessage());
			msg = srm;
		}
		return msg;
	}

	@Override
	public double getDealerBalance() {
		BalanceRequestMsg msg = new BalanceRequestMsg();
		BalanceContent balanceContent = new BalanceContent();
		balanceContent.setMerchant(dealerInfo.getDeawerAccount());
		msg.setContent(balanceContent);
		BalanceResponseMsg brm = getBody(BalanceResponseMsg.class, getContent("806", msg));
		return Double.parseDouble(brm.getBalance());
	}

	@Override
	protected String getRemark(String code) {
		return code + ":" + ShenFuUtil.getErrorMessage(code);
	}

	private <T extends AbstractXml> T getBody(Class<T> cla, Content content) {
		T brm = null;
		String code = content.getHead().getErrorcode();
		if (SUCCESS.equals(code)) {
			//不存在内容
			if("".equals(content.getBody())){
				throw new ResponseFaillRuntimeException("5", getRemark("5"));
			}
			String xmlStr = DESUtil.decrypt(content.getBody(), dealerInfo.getAccountPassword());
			try {
				LOGGER.info("神福出票商响应解密"+xmlStr);
				brm = cla.newInstance().fromXML(xmlStr);
			} catch (Exception e) {
				throw new ServiceRuntimeException("xml格式转换错误" + xmlStr, e);
			}
		} else {
			throw new ResponseFaillRuntimeException(code, getRemark(code));
		}
		return brm;
	}

	/**
	 * 获取解密内容
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年6月22日 下午7:27:48
	 * @param xml
	 * @return
	 */
	private <R extends AbstractXml> Content getContent(String command, R msg) {
		String result = responseDealer(command, msg);
		Content content;
		try {
			content = new Content().fromXML(result);
		} catch (Exception e) {
			throw new ServiceRuntimeException("xml格式转换错误" + result, e);
		}
		return content;
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
	protected Head getHead(String command) {
		Head head = new Head();
		head.setMerchant(dealerInfo.getDeawerAccount());
		head.setTimestamp(DateUtil.getNow(DateUtil.DATE_FORMAT_NUM));
		head.setCommand(command);
		head.setMessageid(head.getMerchant() + command + head.getTimestamp());
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
	protected <R extends AbstractXml> String responseDealer(String command, R msg) {
		String content = msg.toXml().replace("<?xml version=\"1.0\" ?>", "");;
		Content requestMsg = new Content();
		requestMsg.setHead(getHead(command));
		requestMsg.setSignature(getMd5(requestMsg.getHead()));
		requestMsg.setBody(DESUtil.encrypt(content, dealerInfo.getAccountPassword()));
		String msgStr = requestMsg.toXml();
		String result = null;
		try {
			LOGGER.info("神福出票商请求原始："+content);
			LOGGER.info("神福出票商请求加密：" + msgStr);
			result = HttpUtil.doPostCharset(dealerInfo.getSendUrl(), "UTF-8", "xml="+msgStr);
			LOGGER.info("神福出票商响应" + result);
		} catch (Exception e) {
			LOGGER.error("神福出票商请求异常", e);
			result = e.getMessage();
		}
		return result;
	}

	/**
	 * MD5 检验码
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年6月22日 下午7:08:08
	 * @param head
	 * @return
	 */
	private String getMd5(Head head) {
		StringBuilder sb = new StringBuilder(head.getCommand());
		sb.append(head.getTimestamp());
		sb.append(head.getMerchant());
		sb.append(dealerInfo.getAccountPassword());
		return Md5Util.md5_32(sb.toString(), "UTF-8");
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
		Map<String, MatchOdds> map = new HashMap<>();
		//20140311002(3_3.550,4_5.600)|20140311004(2_3.100,3_3.800)
		//20140501003*3006(0_1.430,1_4.250,3_5.150)|20140501006*3010(0_7.200,1_4.750,3_1.290)
		String[] matchs = StringUtils.tokenizeToStringArray(odd, "|");
		for (String match : matchs) {
			String [] str = StringUtils.tokenizeToStringArray(match, "()_,");
			for (int i = 1; i < str.length; i+=2) {
				MatchOdds mo = new MatchOdds();
				mo.setOdd(str[i+1]);
				String[] tr  = str[i].split(":");
				String play = tr[0];
				if(tr.length == 2){
					mo.setTarget(tr[0]);
					play = tr[1];
				}
				map.put(str[0]+"_" + play, mo);
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
		// 20161024001(3,0)|20161024002(1,0)|20161024004(1,0)|20161024006(1,0)^
		// 20161024003*3006(0)|20161024004*3008(3,4)^
		List<MatchInfo> result = new ArrayList<>();
		String[] cts = StringUtils.tokenizeToStringArray(channelContent.split("-")[1], "|^");
		for (int i = 0; i < cts.length; i++) {
			String[] ms = StringUtils.tokenizeToStringArray(cts[i], "(,)");
			String match = ms[0];
			StringBuilder sb = new StringBuilder();
			for (int j = 1; j < ms.length; j++) {
				if (sb.length() > 0) {
					sb.append("/");
				}
				sb.append(ms[j]);
			}
			MatchInfo mi = new MatchInfo(match, sb.toString());
			result.add(mi);
		}
		return result;
	}
	
	public static void main(String[] args) {
		//ShenFuDealer dealer= new ShenFuDealer("1", "GVHt863fh2WW1olgE0ixpQ==", null);
		/*Map<String, MatchOdds> s = dealer.getMatchOddInfo("20180626039*3007(20_16.0,13_13.0)|20180626040*3007(20_27.0,03_10.0)|20180627041*3006(3_4.7)|20180627042*3010(1_5.95,0_1.11)");
		System.out.println(JsonUtil.object2Json(s));
		System.out.println(JsonUtil.objectList2Json(dealer.getMatchInfo("301114001-20180628045*3006(1)|20180628046*3010(0)|20180628047*3006(1)|20180628048*3007(13)^")));
		String string  = dealer.doMatchOddSport("20180628046*3006(+1.0:3_1.54,+1.0:1_3.85)|20180630101*3010(3_1.85)", "301114001-20180628046*3006(3,1)|20180630101*3010(3)^");
		System.out.println(string);*/
	}
	
}
