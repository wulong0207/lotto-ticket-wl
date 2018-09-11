package com.hhly.ticket.service.ticket.dealer.yuanrunde.cathectic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
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
import com.hhly.ticket.service.ticket.dealer.yuanrunde.CathecticReponseMsg;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.CathecticRequestMsg;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.Check;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.Message;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.NotifyTicket;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.Order;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.Ticket;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.TicketOdds;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.reponse.CathecticReponseHead;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.reponse.CathecticReponseMessage;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.reponse.CathecticReponseOrder;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.request.CathecticHead;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.request.CathecticMessage;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.request.CathecticOrder;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.request.CathecticResultMessage;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.request.CathecticResultOrder;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.util.YuanRunDeUtil;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.DateUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;

public class YuanRunDeDealer extends AbstractDealer implements INotify{

	public YuanRunDeDealer(IOrderService orderService) {
		super(orderService);
	}
	
	public YuanRunDeDealer(ChannelBO bo, IOrderService orderService) {
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
	

	public YuanRunDeDealer(String drawerAccount, String accountPassword,String authCode, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(drawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		dealerInfo.setAuthCode(authCode);
		super.dealerInfo = dealerInfo;
	}
	

	@Override
	public boolean sendTicket(List<TicketBO> ticket) {
		CathecticRequestMsg msg = new CathecticRequestMsg();
		CathecticHead head = new CathecticHead();
		head.setCommandCode("101");
		head.setMerchantCode(dealerInfo.getDeawerAccount());
		head.setTimestamp(DateUtil.getNow("yyyyMMddHHmmss"));
		head.setMsgId(head.getMerchantCode()+head.getCommandCode()+head.getTimestamp());
		String ylSignature = head.getCommandCode()+head.getTimestamp()+head.getMerchantCode()+dealerInfo.getAccountPassword();
		msg.setSignature(Md5Util.md5_32(ylSignature, "UTF-8"));
		msg.setHead(head);

		CathecticMessage cathecticMessage = new CathecticMessage();
		List<CathecticOrder> orders = new ArrayList<>();
		ticket.forEach(t ->{
			String batchNum = dealerInfo.getPreBatch() + TicketUtil.getOrderNo();
			t.setBatchNum(batchNum);
			t.setBatchNumSeq("1");
			CathecticOrder cathecticOrder = new CathecticOrder();
			cathecticOrder.setAdd(t.getLottoAdd()+"");
			cathecticOrder.setBetcode(t.getChannelTicketContent());
			cathecticOrder.setId(batchNum);
			cathecticOrder.setSaleId(t.getChannelLotteryCode());
			cathecticOrder.setPlayerId(t.getChannelPlayType());
			cathecticOrder.setMoney(Integer.parseInt((new BigDecimal(t.getTicketMoney()).toString()))+"");
			cathecticOrder.setMultiple(t.getMultipleNum().toString());
			cathecticOrder.setTermNum(t.getLotteryIssue());
			cathecticOrder.setEndtime(DateUtil.convertDateToStr(t.getEndTicketTime(),"yyyy-mm-dd hh:mm:ss"));
			orders.add(cathecticOrder);
			cathecticMessage.setOrders(orders);
		});
		String bodyMessage = cathecticMessage.toXml();
		bodyMessage = bodyMessage.replace("<?xml version=\"1.0\" ?>", "");
		LOGGER.info("元润德请求的body:"+bodyMessage);
		msg.setBody(YuanRunDeUtil.desEncrypt(bodyMessage,dealerInfo.getAccountPassword()));
		String result = requestOrder(msg);
		CathecticReponseMsg cathecticReponseMsg = analysisXMl(result);
		//广东11选5  <message><orders><order><id>1533811351946</id><sysid>SYS201808090000313619</sysid><errorCode>0</errorCode><msg>投注成功</msg></order></orders></message>
		String message = YuanRunDeUtil.desDecrypt(cathecticReponseMsg.getBody(), dealerInfo.getAccountPassword());
		LOGGER.info("元润德请求的解密body:"+message);
		String errorCode = cathecticReponseMsg.getHead().getErrorCode();
		if("0".equals(errorCode)){
			CathecticReponseMessage cathecticReponseMessage = analysisMessageXML(message);
			List<CathecticReponseOrder> cathecticReponseOrders = cathecticReponseMessage.getOrders();
			ticket.forEach(t->{
				cathecticReponseOrders.forEach(pt->{
					if(t.getBatchNum().equals(pt.getId())){
						if("0".equals(pt.getErrorCode())){//送票成功
							t.setThirdNum(pt.getSysid());
							t.setTicketStatus(TicketStatus.SEND.getValue());
							t.setTicketRemark("送票成功");
						}else{
							t.setTicketStatus(TicketStatus.SEND_FAIL.getValue());
							t.setTicketRemark(pt.getMsg());
						}
					}
				});
			});
			return true;
		}
		else{
			String errorMessage = YuanRunDeUtil.ERROR_CODE.get(errorCode);
			if(errorMessage!=null&&!"".equals(errorMessage)){
				throw new ServiceRuntimeException(errorMessage);
			}else{
				return false;
			}
		}
	}

	@Override
	public double getDealerBalance() {
		CathecticRequestMsg msg = new CathecticRequestMsg();
		CathecticHead head = new CathecticHead();
		head.setCommandCode("104");
		head.setMerchantCode(dealerInfo.getDeawerAccount());
		head.setTimestamp(DateUtil.getNow("yyyyMMddHHmmss"));
		head.setMsgId(head.getMerchantCode()+head.getCommandCode()+head.getTimestamp());
		msg.setHead(head);
		String ylSignature = head.getCommandCode()+head.getTimestamp()+head.getMerchantCode()+dealerInfo.getAccountPassword();
		msg.setSignature(Md5Util.md5_32(ylSignature, "UTF-8"));
		msg.setBody(YuanRunDeUtil.desEncrypt("<message><content><merchantCode>"+dealerInfo.getDeawerAccount()+"</merchantCode></content></message>",dealerInfo.getAccountPassword()));
		String result = requestOrder(msg);
		CathecticReponseMsg cathecticReponseMsg = analysisXMl(result);
		String errorCode = cathecticReponseMsg.getHead().getErrorCode();
		if("0".equals(errorCode)){
			Document document = null;
			String message = YuanRunDeUtil.desDecrypt(cathecticReponseMsg.getBody(), dealerInfo.getAccountPassword());
			LOGGER.info("元润德请求的解密body:"+message);
			try {
				document = DocumentHelper.parseText(message);
			} catch (DocumentException e) {
				throw new ServiceRuntimeException("解析查询平台余额xml格式转换错误" + message, e);
			}
        	Element ele = document.getRootElement();
			return Double.parseDouble(ele.element("balance").getText());
		}
		else{
			String errorMessage = YuanRunDeUtil.ERROR_CODE.get(errorCode);
			if(errorMessage!=null&&!"".equals(errorMessage)){
				LOGGER.error("元润德查询平台余额错误："+errorMessage);
			}else{
				LOGGER.info("查询平台余额失败");
			}
			return 0;
		}
	}

	@Override
	public String notifyOutTicket(String msg) {
		if (StringUtils.isEmpty(msg)) {
			return "";
		}
		List<TicketOdds> odds = null;
		CathecticReponseMsg cathecticReponseMsg = analysisXMl(msg);
		CathecticReponseHead head = cathecticReponseMsg.getHead();
		String ylSignature = head.getCommandCode()+head.getTimestamp()+head.getMerchantCode()+dealerInfo.getAccountPassword();
		StringBuffer resultMsg = new StringBuffer();
		resultMsg.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		resultMsg.append("<response>");
		resultMsg.append("<merchantCode>");
		resultMsg.append(dealerInfo.getDeawerAccount());
		resultMsg.append("</merchantCode>");
		resultMsg.append("<commandCode>201</commandCode>");
		String errorCode = "0";
		if(cathecticReponseMsg.getSignature().equals(Md5Util.md5_32(ylSignature, "UTF-8"))){
			String messagebody = null;
			try {
				messagebody = YuanRunDeUtil.desDecrypt(cathecticReponseMsg.getBody().replaceAll(" ", "+"), dealerInfo.getAccountPassword());
				LOGGER.info("元润德回调通知的解密body:"+messagebody);
				odds = anysisBody(messagebody);
			} catch (Exception e) {
				LOGGER.error("元润德回调通知的解密body异常:"+e.getMessage(), e);
				errorCode = "90015";
			}
		}else{
			errorCode = "90015";
		}
		resultMsg.append("<errorCode>"+errorCode+"</errorCode>");
		NotifyTicket notifyTicket = new NotifyTicket();
		notifyTicket.setTickets(odds);
		handleCheckTicket(notifyTicket);
		resultMsg.append("</response>");
		return resultMsg.toString();
	}

	/**
	 * 解析元润的body
	 * @param messagebody
	 * @return
	 */
	private List<TicketOdds> anysisBody(String messagebody) {
		List<TicketOdds> odds;
		Message message = new Message().fromXML(messagebody);
		odds = new ArrayList<>();
		List<Order> orders = message.getOrders();
		if(orders==null||orders.size()==0){
			orders = message.getRecords();
		}
		for(Order order : orders){
			TicketOdds ticketOdds = new TicketOdds();
			ticketOdds.setId(order.getId());
			ticketOdds.setErrorCode(order.getErrorCode());
			Ticket ticket = order.getTicketlist().get(0);
			ticketOdds.setOdds(ticket.getPlv());
			ticketOdds.setPrinttime(ticket.getPrinttime());
			odds.add(ticketOdds);
		}
		return odds;
	}
	
	@Override
	protected String doMatchOdd(String odd, String channelContent) {
		return doMatchOddSport(odd, channelContent);
	}

	@Override
	public String getSuccessReuslt(String... code) {
		return null;
	}
	
	@Override
	protected String getRemark(String code) {
		return YuanRunDeUtil.ERROR_CODE.get(code.trim());
	}

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

	@Override
	public boolean isPassMode(TicketBO bo) {
		//广东十一选五前一  只支持复式
		if(bo.getLotteryChildCode()==21009&&bo.getContentType()!=2){
			return false;
		}
		// 广东十一选五前二直选,前三直选  暂不支持
		else if(bo.getLotteryChildCode()==21011||bo.getLotteryChildCode()==21013){
			return false;
		}
		return true;
	}
	
	
	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		CathecticRequestMsg msg = new CathecticRequestMsg();
		CathecticHead head = new CathecticHead();
		head.setCommandCode("102");
		head.setMerchantCode(dealerInfo.getDeawerAccount());
		head.setTimestamp(DateUtil.getNow("yyyyMMddHHmmss"));
		head.setMsgId(head.getMerchantCode()+head.getCommandCode()+head.getTimestamp());
		String ylSignature = head.getCommandCode()+head.getTimestamp()+head.getMerchantCode()+dealerInfo.getAccountPassword();
		msg.setSignature(Md5Util.md5_32(ylSignature, "UTF-8"));
		msg.setHead(head);
		CathecticResultMessage cathecticResultMessage = new CathecticResultMessage(); 
		List<CathecticResultOrder> orders = new ArrayList<>();
		list.forEach(l->{
			CathecticResultOrder order = new CathecticResultOrder();
			order.setId(l.getBatchNum());
			orders.add(order);
		});
		cathecticResultMessage.setOrders(orders);
		String bodyMessage = cathecticResultMessage.toXml();
		bodyMessage = bodyMessage.replace("<?xml version=\"1.0\" ?>", "");
		LOGGER.info("元润德请求的body:"+bodyMessage);
		msg.setBody(YuanRunDeUtil.desEncrypt(bodyMessage,dealerInfo.getAccountPassword()));
		String result = requestOrder(msg);
		
		CathecticReponseMsg cathecticReponseMsg = analysisXMl(result);
		Check check = new Check();
		check.setCode(cathecticReponseMsg.getHead().getErrorCode());
		if("0".equals(check.getCode())){
			String messagebody = YuanRunDeUtil.desDecrypt(cathecticReponseMsg.getBody(), dealerInfo.getAccountPassword());
			LOGGER.info("元润的回调通知成功的解密body:"+messagebody);
			check.setTicketOdds(anysisBody(messagebody));
		}
		else{
			LOGGER.info("元润的回调通知失败");	
		}
		return check;
	}
	
	private String requestOrder(CathecticRequestMsg msg) {
		String result = null;
		String string = msg.toXml();
		String requestResult = string.replace("<?xml version=\"1.0\" ?>", "<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		try {
			LOGGER.info("元润德请求的内容:"+requestResult);
			Map<String, String> map = new HashMap<>();
			map.put("xml", requestResult);
			result = HttpUtil.doPostCharset(dealerInfo.getSendUrl(), "utf-8", map);
			LOGGER.info("元润德返回的内容"+result);
		} catch (Exception e) {
			LOGGER.error("元润德请求异常");
			result = e.getMessage();
		}
		return result;
	}
	
	private CathecticReponseMsg analysisXMl(String result){
		CathecticReponseMsg cathecticReponseMsg = null;
		try {
			cathecticReponseMsg = new CathecticReponseMsg().fromXML(result);
		} catch (Exception e) {
			throw new ServiceRuntimeException("xml格式转换错误" + result, e);
		}
		return cathecticReponseMsg;
	}
	
	private CathecticReponseMessage analysisMessageXML(String message){
		CathecticReponseMessage cathecticReponseMessage = null;
		try {
			cathecticReponseMessage = new CathecticReponseMessage().fromXML(message);
		} catch (Exception e) {
			throw new ServiceRuntimeException("message,xml格式转换错误" + message, e);
		}
		return cathecticReponseMessage;
	} 

	public static void main(String[] args) {
//		StringBuffer body = new StringBuffer();
////		body.append("<?xml version=\"1.0\" ?>");
//		body.append("<message>");
//		body.append("<orders>");
//		body.append("<order>");
//		body.append("<id>00000000</id>");
//		body.append("<ticketlist>");
//		body.append("<ticket plv=\"20160606012(23_34.00)|20160606013(52_33.00)\" printtime=\"2018-08-10 10:55:00\"></ticket>");
////		body.append("<ticket plv=\"20160606013(23_34.00)|20160606014(52_33.00)\" printtime=\"2018-08-10 10:56:00\"></ticket>");
//		body.append("</ticketlist>");
//		body.append("<errorCode>0</errorCode> ");
//		body.append("</order>");
//		body.append("</orders>");
//		body.append("</message>");
//		
//		Message message = new Message().fromXML(body.toString());
//		List<TicketOdds> odds = new ArrayList<>();
//		List<Order> orders = message.getOrders();
//		for(Order order : orders){
//			TicketOdds ticketOdds = new TicketOdds();
//			ticketOdds.setId(order.getId());
//			ticketOdds.setErrorCode(order.getErrorCode());
//			Ticket ticket = order.getTicketlist().get(0);
//			ticketOdds.setOdds(ticket.getPlv());
//			ticketOdds.setPrinttime(ticket.getPrinttime());
//			odds.add(ticketOdds);
//		}
		
		
//		String message = body.toString();
//		LOGGER.info("元润德请求的解密body:"+message);
//			LOGGER.info("查询平台余额成功");
//			Document document = null;
//			try {
//				document = DocumentHelper.parseText(message);
//			} catch (DocumentException e) {
//				throw new ServiceRuntimeException("解析查询平台余额xml格式转换错误" + message, e);
//			}
//        	Element employees = document.getRootElement();    //获得根节点
//        	Element orders = employees.element("orders");
//        	Element order = orders.element("order");
//        	System.out.println(order.elementText("id"));
//        	String errorCode = order.element("errorCode").getText();
//            if("0".equals(errorCode)){ //出票成功
//            	Element ticketlist = order.element("ticketlist");
//            	for(Iterator j = ticketlist.elementIterator(); j.hasNext();){
//            		 Element node=(Element) j.next();
//            		 String plv = node.attributeValue("plv");
//            		 String printtime = node.attributeValue("printtime");
//            		 System.out.println("plv:"+plv+",printtime:"+printtime);
//            	}
//            }
//            else if("2".equals(errorCode)){ //出票失败
//            	
//            }else{//处理中
//            	
//            }
		
		
		String mes = "<message><orders><order><id>YRD18081015245414500002</id><sysid>SYS201808100000312382</sysid><errorCode>90012</errorCode><msg>投注订单错误</msg></order></orders></message>";
		try {
			CathecticReponseMessage cathecticReponseMessage = new CathecticReponseMessage().fromXML(mes);
		} catch (Exception e) {
			throw new ServiceRuntimeException("message,xml格式转换错误" + mes, e);
		}
	}

}
