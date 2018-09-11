package com.hhly.ticket.service.ticket.dealer.ruilang.cathectic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.INotify;
import com.hhly.ticket.service.ticket.dealer.ruilang.BalanceRequestMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.BalanceResponseMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.CathecticRequestMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.CathecticResponseMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.Ctrl;
import com.hhly.ticket.service.ticket.dealer.ruilang.NotifySuccessMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.NotifyTicketResponseMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.RuiLangUtil;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.Balance;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.Cathectic;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.CathecticOrder;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.CathecticTicket;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.CathecticUserInfo;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.CheckOrder;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.CheckOrderTicket;
import com.hhly.ticket.util.PropertyUtil;

/**
 * @desc 投注接口
 * @author jiangwei
 * @date 2017年5月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class RuilangDealer extends AbstractRuiLangDealer implements INotify { 
	//去除重复处理订单
	private static Map<String,Long> OUT_TICKET = new ConcurrentHashMap<>();
	//睿朗投注参数
	private static String USERNAME;
	private static String CARDNO;
	private static String MOBILE;
	private static String REALNAME;
	private static String EMAIL;
	
	private  final List<String> pass;
	static{
		USERNAME = PropertyUtil.getPropertyValue("application.properties", "rl_username");
		CARDNO = PropertyUtil.getPropertyValue("application.properties", "rl_cardno");
		MOBILE = PropertyUtil.getPropertyValue("application.properties", "rl_mobile");
		REALNAME = PropertyUtil.getPropertyValue("application.properties", "rl_realname");
		EMAIL = PropertyUtil.getPropertyValue("application.properties", "rl_email");
	}
	{
		pass = new ArrayList<>();
		pass.add("1_1");
		pass.add("2_1");
		pass.add("3_1");
		pass.add("4_1");
	}
	public RuilangDealer(ChannelBO bo, IOrderService orderService,int channelType) {
		super(bo, orderService);
		this.channelType = channelType;
	}

	/**
	 * 初始化 INotify 构造器
	 * 
	 * @param orderService
	 * @param alarmInfoService
	 */
	public RuilangDealer(String deawerAccount, String accountPassword, IOrderService orderService) {
		super(deawerAccount, accountPassword, orderService);
	}

	/**
	 * 数字彩送票接口编号
	 */
	private static final String NUMBER_SEND_CMD = "2001";
	/**
	 * 竞彩
	 */
	private static final String SPORT_SEND_CMD = "2030";
	/**
	 * 北京单场，胜负过关
	 */
	private static final String SPORT_SINGLE = "2006";

	@Override
	public boolean sendTicket(List<TicketBO> ticket) {
		String beathNum = getBatchNum();
		TicketBO bo = ticket.get(0);
		Cathectic cathectic = new Cathectic();
		CathecticOrder order = new CathecticOrder();
		cathectic.setOrder(order);
		order.setAreaid("00");
		order.setIssue(bo.getChannelLotteryIssue());
		order.setLotoid(bo.getChannelLotteryCode());
		order.setOrderno(beathNum);
		order.setUsername(USERNAME);
		CathecticUserInfo cathecticUserInfo = new CathecticUserInfo();
		order.setUserinfo(cathecticUserInfo);
		cathecticUserInfo.setCardno(CARDNO);
		cathecticUserInfo.setCardtype("1");
		cathecticUserInfo.setMobile(MOBILE);
		cathecticUserInfo.setRealname(REALNAME);
		cathecticUserInfo.setEmail(EMAIL);
		int i = 1;
		String seq = "";
		for (TicketBO ticketBO : ticket) {
			seq = String.valueOf(i);
			ticketBO.setBatchNum(beathNum);
			ticketBO.setBatchNumSeq(seq);
			order.getTicket().add(new CathecticTicket(seq, ticketBO.getChannelTicketContent()));
			i++;
			if(StringUtils.isNotBlank(ticketBO.getRedeemCode())){
				if(ticket.size() > 1){
					throw new ServiceRuntimeException("中乐彩积分兑换每次只能出一张票");
				}
				cathecticUserInfo.setEmail("#2A" + ticketBO.getRedeemCode());
			}
		}
		CathecticRequestMsg msg = new CathecticRequestMsg();
		msg.setBody(cathectic);
		String cmd = getCmd(bo.getLotteryCode());
		msg.setCtrl(getCtrl(cmd, cathectic));
		String result = responseDealer(msg);
		CathecticResponseMsg response = getResponseMsg(CathecticResponseMsg.class, result);
		String errorCode = response.getBody().getResponse().getErrorcode();
		if (SUCCESS.equals(errorCode)) {
			sendTicketSuccess(ticket);
			return true;
		} else {
			sendTicketFial(ticket, response);
			return false;
		}
	}
	/**
	 * 判断出票接口
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月19日 下午6:17:52
	 * @param lotteryCode
	 * @return
	 */
	private String getCmd(int lotteryCode) {
		if(lotteryCode == 300 || lotteryCode == 301){
			return SPORT_SEND_CMD;
		}else if(lotteryCode == 306 || lotteryCode == 307){
			return SPORT_SINGLE;
		}
		return NUMBER_SEND_CMD;
	}

	/**
	 * 送票失败
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 上午11:54:46
	 * @param ticket
	 * @param response
	 */
	private void sendTicketFial(List<TicketBO> ticket, CathecticResponseMsg response) {
		CathecticOrder order = response.getBody().getResponse().getOrder();
		String error = "";
		List<CathecticTicket> tickets = new ArrayList<>();
		if (order != null) {
			error = order.getError();
			tickets = order.getTicket();
		}
		for (TicketBO ticketBO : ticket) {
			ticketBO.setTicketStatus(TicketStatus.SEND_FAIL.getValue());
			for (CathecticTicket cathecticTicket : tickets) {
				if (ticketBO.getBatchNumSeq().equals(cathecticTicket.getSeq())) {
					ticketBO.setChannelRemark(error+":"+RuiLangUtil.getErrorMessage(error));
					ticketBO.setReceiptContent("送票格式："+cathecticTicket.getContent());
					ticketBO.setTicketRemark("送票出票商失败");
					break;
				}
			}
		}
	}

	@Override
	public String notifyOutTicket(String msg) {
		NotifyTicketResponseMsg responseMsg = getResponseMsg(NotifyTicketResponseMsg.class, msg);
		CheckOrder order = responseMsg.getBody().getOrder();
		String orderno = order.getOrderno();
		if(OUT_TICKET.containsKey(orderno)){
			//过滤重复通知订单
			OUT_TICKET.remove(orderno);
			if(OUT_TICKET.size() >= 5000){
				LOGGER.info("出票商通知出票过滤列队达到上限，进行清理。。。。。。");
				long now = System.currentTimeMillis();
				for (Map.Entry<String, Long> entry : OUT_TICKET.entrySet()) {
					if(now - entry.getValue() > 3000){
						OUT_TICKET.remove(entry.getKey());
					}
				}
			}
		}else{
			OUT_TICKET.put(orderno,System.currentTimeMillis());
			List<TicketBO> tickets = new ArrayList<>();
			String lotoid = order.getLotoid();
			for (CheckOrderTicket ot : order.getTicket()) {
				TicketBO bo = handleCheckOrder(lotoid,orderno, ot);
				if(bo != null){
					tickets.add(bo);
				}
			}
			updateOutTicket(tickets);
		}
		NotifySuccessMsg msSuccessMsg =getNotify();
		msSuccessMsg.setId(responseMsg.getId());
		msSuccessMsg.getCtrl().setCmd(responseMsg.getCtrl().getCmd());
		return msSuccessMsg.toXml();
	}

	@Override
	public String getSuccessReuslt(String ...code) {
		return getNotify().toXml();
	}
    /**
     * 获取成功回执类容
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年8月12日 下午5:55:42
     * @return
     */
	private NotifySuccessMsg getNotify() {
		NotifySuccessMsg msg = new NotifySuccessMsg();
		Ctrl ctrl = getCtrl("", msg.getBody());
		msg.setCtrl(ctrl);
		return msg;
	}
	
	@Override
	public double getDealerBalance() {
		BalanceRequestMsg msg = new BalanceRequestMsg();
		Balance body = new Balance();
		msg.setBody(body);
		msg.setCtrl(getCtrl("2007", body));
		String result = responseDealer(msg);
		BalanceResponseMsg responseMsg = getResponseMsg(BalanceResponseMsg.class, result);
		String allBalance = responseMsg.getBody().getResponse().getAllBalance();
		if(StringUtils.isEmpty(allBalance)){
			return 0.0;
		}
		return Double.parseDouble(allBalance);
	}
	
	@Override
	public boolean isPassMode(TicketBO bo) {
		if(bo.getLotteryCode() == 300){
			return  pass.contains(bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH)[1]) ;	
		}
		return true;
	}
}
