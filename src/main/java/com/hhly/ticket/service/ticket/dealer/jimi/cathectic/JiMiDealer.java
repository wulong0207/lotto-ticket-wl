package com.hhly.ticket.service.ticket.dealer.jimi.cathectic;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.DealerInfo;
import com.hhly.ticket.service.entity.MatchInfo;
import com.hhly.ticket.service.entity.MatchOdds;
import com.hhly.ticket.service.entity.ReceiptContent;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.AbstractDealer;
import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.INotify;
import com.hhly.ticket.service.ticket.dealer.jimi.AbstractMsg;
import com.hhly.ticket.service.ticket.dealer.jimi.BalanceResponseMsg;
import com.hhly.ticket.service.ticket.dealer.jimi.CathecticResponseMsg;
import com.hhly.ticket.service.ticket.dealer.jimi.CheckResponseMsg;
import com.hhly.ticket.service.ticket.dealer.jimi.JiMiUtil;
import com.hhly.ticket.service.ticket.dealer.jimi.response.Ticket;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.TicketUtil;

/**
 * @desc 吉米出票商
 * @author jiangwei
 * @date 2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JiMiDealer extends AbstractDealer implements INotify{

	public static final String SUCCESS = "0";
	
	public static final String RESPONSE_SUCCESS = "SUCCESS";

	public JiMiDealer(ChannelBO bo, IOrderService orderService) {
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
	public JiMiDealer(String deawerAccount, String accountPassword, IOrderService orderService) {
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
		StringBuilder message = new StringBuilder();
		for (TicketBO ticket : tickets) {
			ticket.setBatchNum(dealerInfo.getPreBatch() + TicketUtil.getOrderNo());
			ticket.setBatchNumSeq("1");
			if (message.length() > 1) {
				message.append("|");
			}
			message.append(ticket.getBatchNum()).append("#").append(ticket.getChannelTicketContent()).append("#")
					.append(ticket.getChips()).append("#").append(ticket.getMultipleNum()).append("#")
					.append((int) ticket.getTicketMoney());
		}
		TicketBO bo = tickets.get(0);
		String result = responseDealer("BetTicket", dealerInfo.getDeawerAccount(), bo.getChannelLotteryCode(),
				bo.getChannelLotteryIssue(), message.toString());
		CathecticResponseMsg msg = getResponseMsg(CathecticResponseMsg.class, result);
		if (SUCCESS.equals(msg.getHeader().getErrorCode())) {
			sendTicketSuccess(tickets, msg.getBody().getTickets());
			return true;
		} else {
			sendTicketFail(tickets, msg.getHeader().getErrorCode());
			return false;
		}
	}

	/**
	 * 处理送票成功
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月14日 下午4:30:36
	 * @param tickets
	 * @param tickets2
	 */
	private void sendTicketSuccess(List<TicketBO> tickets, List<Ticket> reponseTicket) {
		for (TicketBO ticketBO : tickets) {
			for (Ticket ticket : reponseTicket) {
				if (ticketBO.getBatchNum().equals(ticket.getOrdersID())) {
					String code = ticket.getErrorCode();
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
	 * 发送出票商响应
	 */
	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		StringBuilder sb = new StringBuilder();
		for (DealerCheckBO dealerCheckBO : list) {
			if(sb.length() > 0){
				sb.append(",");
			}
			sb.append(dealerCheckBO.getBatchNum());
		}
		String rs = responseDealer("QueryTicket",dealerInfo.getDeawerAccount(),sb.toString());
		ICheckResponse msg = getResponseMsg(CheckResponseMsg.class, rs);
		return msg;
	}


	@Override
	public double getDealerBalance() {
		String result = responseDealer("QueryBalance",dealerInfo.getDeawerAccount());
		BalanceResponseMsg msg = getResponseMsg(BalanceResponseMsg.class, result);
		if (SUCCESS.equals(msg.getHeader().getErrorCode())) {
			return msg.getBody().getBalance();
		}
		throw new ServiceRuntimeException(
				"获取出票商余额错误" + msg.getHeader().getErrorCode() + "|" + msg.getHeader().getErrorMsg());
	}

	@Override
	protected String getRemark(String code) {
		return JiMiUtil.getErrorMessage(code);
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
	private <T extends AbstractMsg> T getResponseMsg(Class<T> cla, String xml) {
		String md5;
		T t;
		try {
			t = cla.newInstance().fromXML(xml);
			int start = xml.indexOf("<body>");
			int end = xml.indexOf("</body>") + 7;
			String body = xml.substring(start, end);
			String encrypt = URLEncoder.encode(getEncrypt(body), "UTF-8");
			md5 = DigestUtils.md5Hex(encrypt);
		} catch (Exception e) {
			throw new ServiceRuntimeException("xml格式转换错误" + xml, e);
		}
		if (Objects.equals(md5, t.getHeader().getSign())) {
			return t;
		} else {
			throw new ServiceRuntimeException("加密验证错误：" + xml);
		}
	}

	/**
	 * 发送请求
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月14日 下午3:08:38
	 * @param transactionType
	 * @return
	 */
	private String responseDealer(String method, String... param) {
		StringBuilder sb = new StringBuilder();
		String[] parameters = new String[param.length + 1];
		for (int i = 0; i < param.length; i++) {
			sb.append(param[i]);
			parameters[i] = param[i];
		}
		LOGGER.info("吉米请求：" + sb.toString());
		sb.append(dealerInfo.getAccountPassword());
		parameters[parameters.length - 1] = DigestUtils.md5Hex(sb.toString());
		String result = "";
		try {
			Service service = new Service();
			Integer timeout = Integer.parseInt("20000");
			Call call = (Call) service.createCall();
			call.setUseSOAPAction(true);
			call.setTargetEndpointAddress(new java.net.URL(dealerInfo.getSendUrl()));
			call.setEncodingStyle("http://schemas.xmlsoap.org/wsdl/soap/");
			call.setOperationName(new QName("http://service.xixi.com/", method));
			call.setTimeout(timeout);
			result = (String) call.invoke(parameters);
			LOGGER.info("吉米响应：" + result);
		} catch (Exception ex) {
			throw new ServiceRuntimeException("吉米请求出票商请求异常", ex);
		}
		return result;
	}

	/**
	 * 获取加密字符串
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月1日 上午10:04:36
	 * @param body
	 * @return
	 */
	protected String getEncrypt(String body) {
		return dealerInfo.getDeawerAccount() + body + dealerInfo.getAccountPassword();
	}

	/**
	 * 查询彩期
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月17日 下午3:08:41
	 * @param code
	 */
	public void searchIssue(String code) {
		System.out.println(responseDealer("QueryIssueSell",dealerInfo.getDeawerAccount(),code));
	}
	
	
	@Override
	public String notifyOutTicket(String msg) {
		ICheckResponse cr = getResponseMsg(CheckResponseMsg.class, msg);
		handleCheckTicket(cr);
		return getSuccessReuslt(RESPONSE_SUCCESS);
	}

	
	@Override
	public String getSuccessReuslt(String... code) {
		if(code.length >=1){
			return code[0];
		}
		return "fail";
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
		// F20141027001,0:1.35/1:2.15//F20141027002,3:1.35
		// F20141027001,0:1.35-105//F20141027002,7:1.35-103
		// B20141027301,1(-3.5):1.35//B20141027302,0(-2.5):1.35
		// B20141027301,1(3.5):1.35-152//B20141027302,1(198.5):1.75-154
		Map<String, MatchOdds> map = new HashMap<>();
		String[] matchs = odd.split("//");
		for (String match : matchs) {
			String wf_1 = match;
			String wf_2 = null;
			if("-".equals(match.substring(match.length()-3,match.length()-2))){
				wf_2 = match.substring(match.length()-2);
				wf_1 = match.substring(0,match.length()-3);
			}
			String[] str = wf_1.split("[,/]");
			String no = str[0];
			if (wf_2 != null) {
				no = no + "_" + wf_2;
			}
			for (int i = 1; i < str.length; i++) {
				int beginIndex = str[i].lastIndexOf(":");
				String start = str[i].substring(0,beginIndex);
				String end = str[i].substring(beginIndex+1);
				MatchOdds matchOdds = new MatchOdds();
				String[] py = StringUtils.tokenizeToStringArray(start, "()");
				if (py.length == 2) {
					matchOdds.setTarget(py[1]);
				}
				matchOdds.setOdd(end);
				map.put(no + "_" + py[0], matchOdds);
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
		// 4*1^F201611281001,3/1-105//F201611281002,0/1-101//F201611281003,0-103//F201611281004,3-105
		List<MatchInfo> result = new ArrayList<>();
		String[] matchs = channelContent.split("\\^")[1].split("//");
		for (String match : matchs) {
			String[] wf = match.split("-");
			String[] str = wf[0].split(",");
			String m = str[0];
			if (wf.length == 2) {
				m = m + "_" + wf[1];
			}
			MatchInfo mi = new MatchInfo(m, str[1]);
			result.add(mi);
		}
		return result;
	}

	public static void main(String[] args) {
		// "2*1^B20141027301,1-152//B20141027302,1-154" ;
		JiMiDealer gDealer = new JiMiDealer("324", "byhEVc9gX2j/03QaxwR5DA==", null);
		TicketBO bo = new TicketBO();
		ReceiptContent string = gDealer.getOdd(bo, "B20141027301,1(3.5):1.35-152//B20141027302,1(198.5):1.75-154");
		System.out.println(string.getReceiptContent());
	}

	
}
