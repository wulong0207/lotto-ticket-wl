package com.hhly.ticket.service.ticket.dealer.ruilang.cathectic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.common.OrderEnum.TicketStatus;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.DealerInfo;
import com.hhly.ticket.service.entity.ReceiptContent;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.IXml;
import com.hhly.ticket.service.ticket.dealer.AbstractDealer;
import com.hhly.ticket.service.ticket.dealer.ruilang.AbstractMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.CheckOrderRequestMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.CheckOrderResponseMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.Ctrl;
import com.hhly.ticket.service.ticket.dealer.ruilang.IssueRequestMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.IssueResponseMsg;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.CheckOrder;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.CheckOrderBody;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.Issue;
import com.hhly.ticket.service.ticket.dealer.ruilang.request.IsuueLoto;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.CheckOrderResponse;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.CheckOrderTicket;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.IssueResponse;
import com.hhly.ticket.util.CollectionUtil;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;
/**
 * @desc 睿朗抽象实现
 * @author jiangwei
 * @date 2017年8月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractRuiLangDealer extends AbstractDealer{
	/**
	 * 出票商返回成功标识
	 */
	protected static final String SUCCESS = "0";
	
	
	public AbstractRuiLangDealer(ChannelBO bo,IOrderService orderService ){
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(bo.getDrawerAccount());
		dealerInfo.setAccountPassword(DESUtil.decrypt(bo.getAccountPassword()));
		dealerInfo.setSendUrl(bo.getSendUrl());
		dealerInfo.setSearchMaxTicket(bo.getSearchMaxTicket());
		dealerInfo.setSearchAuto(bo.getSearchAuto());
		dealerInfo.setPreBatch(bo.getPreBatch());
		super.dealerInfo = dealerInfo;
	}
	
	public AbstractRuiLangDealer(String deawerAccount, String accountPassword,IOrderService orderService){
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(deawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}
	/**
	 * 查询彩期
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午2:46:24
	 * @param lottery
	 * @param issue 期号：查询指定期号信息，
	 *              为空，则查询指定彩种的当前在售期次的奖期信息，
	 *              为pre，则查询指定彩种的当前所有预售期次的奖期信息（状态为 0）
	 *              为all，则查询指定彩种的当前所有预售与在售期次的奖期信息（状态为 0 和 1）
	 * @return
	 */
	public IssueResponse searchIssue(String channelCode, String issue) {
		IssueRequestMsg msg = new IssueRequestMsg();
		Issue body = new Issue();
		IsuueLoto loto = new IsuueLoto(channelCode, issue);
		body.setLoto(loto);
		msg.setBody(body);
		String cmd = "2000";
		if (StringUtils.isEmpty(issue) || "all".equals(issue) || "pre".equals(issue)) {
			cmd = "2017";
		}
		msg.setCtrl(getCtrl(cmd, body));
		String result = responseDealer(msg);
		IssueResponseMsg responseMsg = getResponseMsg(IssueResponseMsg.class, result);
		return responseMsg.getBody().getResponse();
	}

	@Override
	public List<String> checkTicket(List<DealerCheckBO> checkBOs) {
		List<String> result = new ArrayList<>();
		List<String> batchNums = new ArrayList<>();
		//北京单场，胜负过关和其他彩种检票接口不同
		for (DealerCheckBO dealerCheckBO : checkBOs) {
			if(dealerCheckBO.getLotteryCode() == 306 || dealerCheckBO.getLotteryCode() == 307){
				List<String> fail = sendCheckTicket("2012",Arrays.asList(dealerCheckBO.getBatchNum()));
				result.addAll(fail);
			}else{
				batchNums.add(dealerCheckBO.getBatchNum());
			}
		}
		//出票商每次只最多查询订单数
		List<List<String>> subList = CollectionUtil.subList(batchNums, dealerInfo.getSearchMaxTicket());
		for (List<String> list : subList) {
			List<String> fail = sendCheckTicket("2025",list);
			result.addAll(fail);
		}
		return result;
	}
	/**
	 * 获取批次号
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月5日 上午10:53:27
	 * @return
	 */
	protected final String getBatchNum() {
		return dealerInfo.getPreBatch() + TicketUtil.getOrderNo();
	}
	/**
	 * 处理出票商返回出票信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月3日 下午2:46:52
	 * @param orderno
	 * @param lotteryCode
	 * @param ot
	 * @return 如果赔率延时 返回null
	 */
	protected TicketBO handleCheckOrder(String lotoid,String orderno, CheckOrderTicket ot) {
		TicketBO bo = new TicketBO();
		bo.setBatchNum(orderno);
		bo.setBatchNumSeq(ot.getSeq());
		bo.setThirdNum(ot.getTicketid());
		bo.setOfficialNum(ot.getGroundid());
		bo.setReceiptContent(ot.getValue());
		String errmsg = ot.getErrmsg();
		if ("Y".equals(ot.getStatus())) {
			// 出票成功
			bo.setTicketStatus(TicketStatus.OUT_TICKET.getValue());
			ReceiptContent rc  = getOdd(bo, errmsg);
			bo.setReceiptContent(rc.getReceiptContent());
			bo.setReceiptContentDetail(rc.getReceiptContentDetail());
			bo.setTicketRemark("出票成功");
			//只有竞彩（不包括单场）需要判断 errmsg
			if("SUCCESS".equalsIgnoreCase(errmsg) && !isBjdc(lotoid)){
				LOGGER.info("竞彩出票成功，但是赔率延迟，未获取到赔率不更新状态："+ot);
				//出票成功，赔率延时，需要重新继续检票
				return null;
			}
			//大乐透保存乐善码
			if("113".equals(lotoid) 
					&& StringUtils.isNotBlank(errmsg) 
					&& errmsg.length() < 100){
				bo.setChannelRemark(errmsg.replaceAll("#", "+"));
			}
		} else if("W".equals(ot.getStatus())){
			//正在出票中
			return null;
		} else {
			bo.setTicketStatus(TicketStatus.ERROR.getValue());
			bo.setChannelRemark(errmsg);
			bo.setTicketRemark("出票失败");
		}
		return bo;
	}
	/**
	 * 判断是否是北京单场
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月31日 下午3:43:11
	 * @param lotoid
	 * @return
	 */
	private boolean isBjdc(String lotoid) {
		if("201".equals(lotoid) 
				||"202".equals(lotoid)
				||"203".equals(lotoid)
				||"204".equals(lotoid)
				||"205".equals(lotoid)
				||"206".equals(lotoid)
				||"207".equals(lotoid)){
			return true;
		}
		return false;
	}

	/**
	 * 解析出票商返回结果
	 * 验证出票商返回加密是否正确
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午4:32:29
	 * @param cla 解析类
	 * @param xml 结果xml
	 * @return
	 */
	protected <T extends AbstractMsg> T getResponseMsg(Class<T> cla,String xml){
		T t;
		String md5;
		try {
			int start = xml.indexOf("<body>");
			int end = xml.indexOf("</body>") + 7;
			String body = xml.substring(start, end);
			md5 = getMd5(body);
			t = cla.newInstance().fromXML(xml);
		} catch (Exception e) {
			throw new ServiceRuntimeException("xml格式转换错误"+xml,e);	
		}
		if(Objects.equals(md5,t.getCtrl().getMd())){
			return t;
		}else{
		   throw new ServiceRuntimeException("加密验证错误："+xml);	
		}
	}
	/**
	 * 认证验证对象
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 上午11:55:35
	 * @param drawerAccount
	 * @param cmd
	 * @param md
	 * @return
	 */
	protected <T extends IXml> Ctrl getCtrl(String cmd,T body){
		Ctrl ctrl = new Ctrl();
    	ctrl.setAgentID(dealerInfo.getDeawerAccount());
    	ctrl.setCmd(cmd);
    	ctrl.setMd(getMd5(body));
    	ctrl.setTimestamp(System.currentTimeMillis());
    	return ctrl;
	}
	/**
	 * 请求
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午12:23:41
	 * @param msg
	 * @return
	 */
	protected <T extends AbstractMsg> String responseDealer(T msg){
		Map<String,String> param = new HashMap<>();
    	param.put("cmd", msg.getCtrl().getCmd());
    	String msgStr =  msg.toXml();
    	param.put("msg", msgStr);
    	String result = null;
    	try {
    		LOGGER.info("睿朗出票商请求" + msgStr);
    		result = HttpUtil.doPost(dealerInfo.getSendUrl(), param);
    		LOGGER.info("睿朗出票商响应" + result);
		} catch (IOException | URISyntaxException e) {
			LOGGER.error(e);
			result = e.getMessage();
		}
    	return result;
	}
	/**
	 * 加密
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午12:17:41
	 * @param body
	 * @return
	 */
	protected <T extends IXml> String getMd5(T body){
		String bodyStr = body.toXml().replace("<?xml version=\"1.0\" ?>","");
    	return getMd5(bodyStr);
	}
	/**
	 * body字符串加密
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午5:46:24
	 * @param body
	 * @return
	 */
	protected String getMd5(String body){
		String md5Str = dealerInfo.getDeawerAccount() +dealerInfo.getAccountPassword() +body;
	    return Md5Util.md5_32(md5Str,"GBK");
	}
	/**
	 * 发送检票请求
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 上午10:47:30
	 * @param batchNum
	 * @return 出牌还未成功的票
	 */
	private List<String> sendCheckTicket(String cmd,List<String> batchNum) {
		CheckOrderBody body = new CheckOrderBody();
		List<CheckOrder> orders = new ArrayList<>();
		for (String str : batchNum) {
			CheckOrder order = new CheckOrder(dealerInfo.getDeawerAccount(), str);
			orders.add(order);
		}
		body.setOrder(orders);
		CheckOrderRequestMsg msg = new  CheckOrderRequestMsg();
		msg.setBody(body);
		msg.setCtrl(getCtrl(cmd, body));//批量查询
		String result = responseDealer(msg);
		if(result == null){
			return batchNum;
		}
		return handleOrder(result,batchNum);
	}
	/**
	 * 处理出票商返回检票信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午8:53:04
	 * @param msg
	 * @return 出票商还在出的批次号
	 */
	private List<String> handleOrder(String msg,List<String> batchNum){
		CheckOrderResponseMsg responseMsg =  getResponseMsg(CheckOrderResponseMsg.class, msg);
		CheckOrderResponse repose= responseMsg.getBody().getResponse();
		List<String> wait = new ArrayList<>();//等待出票的批次号
		if(SUCCESS.equals(repose.getErrorcode())){
			List<TicketBO> tickets = new ArrayList<>();
			for (com.hhly.ticket.service.ticket.dealer.ruilang.response.CheckOrder order : repose.getOrder()) {
				String orderno = order.getOrderno();
				switch (order.getStatus()) {
				case "0"://待出票
					wait.add(orderno);
					break;
				case "2"://部分流单
				case "3"://出票失败
				case "1"://出票成功
					String lotoid = order.getLotoid();
					for (CheckOrderTicket ot : order.getTicket()) {
						TicketBO bo = handleCheckOrder(lotoid,orderno, ot);
						if(bo == null){
							//如果票没有出票成功把订单加入等待出票的批次号集合
							if(!wait.contains(orderno)){
								wait.add(orderno);
							}
						}else{
							tickets.add(bo);
						}
					}
					break;
				case "4"://订单不存在
					break;
				default:
					break;
				}
			}
			//存在出票状态返回的
			updateOutTicket(tickets);
		}else{
			//检查出票失败状态
			orderService.updateOutTicketByBatchNum(batchNum,TicketStatus.ERROR.getValue(),repose.getErrorcode());
			LOGGER.info("检票出票商返回失败，修改票订单出票失败（状态"+repose.getErrorcode()+"）："+batchNum);
		}
		return wait;
	}
}
