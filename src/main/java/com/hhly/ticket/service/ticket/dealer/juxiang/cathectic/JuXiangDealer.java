package com.hhly.ticket.service.ticket.dealer.juxiang.cathectic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
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
import com.hhly.ticket.service.ticket.dealer.juxiang.request.CheckOrder;
import com.hhly.ticket.service.ticket.dealer.juxiang.request.NotifyRequest;
import com.hhly.ticket.service.ticket.dealer.juxiang.request.Order;
import com.hhly.ticket.service.ticket.dealer.juxiang.request.TransRequest;
import com.hhly.ticket.service.ticket.dealer.juxiang.response.NotifyResponse;
import com.hhly.ticket.service.ticket.dealer.juxiang.response.TransResponse;
import com.hhly.ticket.service.ticket.dealer.juxiang.util.JuXiangUtil;
import com.hhly.ticket.service.ticket.dealer.juxiang.util.ThreeDesUtil;
import com.hhly.ticket.service.ticket.dealer.juxiang.util.TransUtil;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.TicketUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @desc 聚享(掌信)出票商接口
 * @author jiangwei
 * @date 2017年9月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JuXiangDealer extends AbstractDealer implements INotify {
	/**
	 * 聚享成功标识
	 */
	public static final String SUCCESS = "0";

	/**
	 * 
	 * @param bo
	 * @param orderService
	 */
	public JuXiangDealer(ChannelBO bo, IOrderService orderService) {
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

	/**
	 * 
	 * @param deawerAccount
	 * @param accountPassword
	 * @param orderService
	 */
	public JuXiangDealer(String deawerAccount, String accountPassword, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(deawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> ticket) {
		if (CollectionUtils.isEmpty(ticket)) {
			return true;
		}
		Map<String, Object> contentMap = new HashMap<>();
		TicketBO first = ticket.get(0);
		contentMap.put("gameId", first.getChannelLotteryCode());
		contentMap.put("issue", first.getChannelLotteryIssue());
		List<Order> orderList = new ArrayList<>();
		for (TicketBO bo : ticket) {
			String batchNum = dealerInfo.getPreBatch() + TicketUtil.getOrderNo();
			bo.setBatchNum(batchNum);
			bo.setBatchNumSeq("1");
			Order order = new Order();
			order.setOrderId(batchNum);
			order.setBetCount(bo.getChips());
			order.setTimeStamp(System.currentTimeMillis());
			order.setBetDetail(bo.getChannelTicketContent());
			order.setTicketMoney(String.valueOf((int) bo.getTicketMoney()));
			orderList.add(order);
		}
		contentMap.put("orderList", orderList);
		TransResponse result = request("200008", contentMap);
		if (SUCCESS.equals(result.getResCode())) {
			JSONObject jsonObject = JSONObject.fromObject(result.getDecryptContent());
			JSONArray array = jsonObject.getJSONArray("orderList");
			handleSuccess(ticket, array);
			return true;
		} else {
			sendTicketFail(ticket, result.getResCode());
			return false;
		}
	}

	/**
	 * 处理出票商成功返回
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月19日 上午11:17:23
	 * @param ticket
	 * @param array
	 */
	private void handleSuccess(List<TicketBO> ticket, JSONArray array) {
		for (int i = 0; i < array.size(); i++) {
			JSONObject con = array.getJSONObject(i);
			String orderId = con.getString("orderId");
			String ticketCode = con.getString("status");
			for (TicketBO bo : ticket) {
				if (bo.getBatchNum().equals(orderId)) {
					if (SUCCESS.equals(ticketCode)) {
						doTicketSuccess(bo);
					} else {
						doTicketFail(bo, con.getString("errCode"));
					}
					break;
				}
			}
		}
	}

	@Override
	protected String getRemark(String code) {
		return code + ":" + JuXiangUtil.getErrorMessage(code);
	}

	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		Map<String, Object> contentMap = new HashMap<>();
		List<CheckOrder> orderList = new ArrayList<>();
		contentMap.put("orderList", orderList);
		for (DealerCheckBO dealerCheckBO : list) {
			CheckOrder order = new CheckOrder(dealerCheckBO.getBatchNum());
			orderList.add(order);
		}
		TransResponse response = request("200009", contentMap);
		response.setResultKey("resultList");
		return response;
	}

	@Override
	public double getDealerBalance() {
		TransResponse transResponse = request("200100", new HashMap<String, Object>());
		if (SUCCESS.equals(transResponse.getResCode())) {
			return JSONObject.fromObject(transResponse.getDecryptContent()).getDouble("balance");
		}
		throw new ServiceRuntimeException("获取余额异常:" + JsonUtil.object2Json(transResponse));
	}

	@Override
	public String notifyOutTicket(String msg) {
		NotifyRequest notifyRequest = receiveNotify(msg);
		if (StringUtils.isEmpty(notifyRequest.getEncryptContent())) {
			return getSuccessReuslt(notifyRequest.getApiCode(), notifyRequest.getMessageId(), "-1");
		}
		if ("300002".equals(notifyRequest.getApiCode())) {
			TransResponse response = new TransResponse();
			response.setResCode(SUCCESS);
			response.setDecryptContent(notifyRequest.getEncryptContent());
			response.setResultKey("notifyList");
			handleCheckTicket(response);
		}
		return getSuccessReuslt(notifyRequest.getApiCode(), notifyRequest.getMessageId(), SUCCESS);
	}

	/**
	 * @see 0:apiCode api接口,
	 * @see 1:messageId 消息id,
	 * @see 2:rescode 返回结果编码
	 */
	@Override
	public String getSuccessReuslt(String... code) {
		String key = dealerInfo.getAccountPassword();
		// hmac密钥，平台提供的密钥的前16位作为hmac-md5算法的key，用于生成消息摘要
		String hmacKey = TransUtil.getHMacKey(key);
		NotifyResponse response = null;
		try {
			// 开始封装通知响应报文
			response = new NotifyResponse(code[0], code[1], code[2], dealerInfo.getDeawerAccount());
			// 加密内容（内容为空字符串）
			String encryptContent = ThreeDesUtil.encryptMode(key, "{}");
			response.setContent(encryptContent);
			// 生成报文摘要值
			String reponseHMac = TransUtil.createHMac(response.getMap(), hmacKey);
			response.setHmac(reponseHMac);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("加密错误", e);
			response = new NotifyResponse(code[0], code[1], "-1", dealerInfo.getDeawerAccount());
		}
		return JsonUtil.object2Json(response);
	}

	public String searchIssue(Lottery lottery, String issue) {
		String channelCode = JuXiangUtil.getCode(lottery);
		Map<String, Object> contentMap = new HashMap<>();
		contentMap.put("gameId", channelCode);
		contentMap.put("issueNum", issue);
		TransResponse reuslt = request("200006", contentMap);
		return reuslt.getDecryptContent();
	}

	/**
	 * 请求出票商
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月18日 下午3:28:31
	 * @param string
	 * @param contentMap
	 * @return
	 */
	private TransResponse request(String transCode, Map<String, Object> contentMap) {
		TransRequest request = new TransRequest();
		request.setApiCode(transCode);
		request.setPartnerId(dealerInfo.getDeawerAccount());
		request.setMessageId("JX" + TicketUtil.getOnlyNo());
		request.setVersion("1.0");
		request.setContent(encryptBody(contentMap));
		request.setHmac(generateHmac(request.getMap()));
		// 发送请求
		String jsonString = null;
		try {
			String requestString = JsonUtil.object2Json(request);
			LOGGER.info("聚享请求:" + requestString + "|" + contentMap.toString());
			jsonString = HttpUtil.doPost(dealerInfo.getSendUrl(), requestString);
		} catch (IOException | URISyntaxException e) {
			LOGGER.info("聚享请求异常：", e);
			throw new ServiceRuntimeException("聚享请求异常", e);
		}
		TransResponse response = null;
		// 对接口返回结果进行处理
		if (null != jsonString) {
			response = JsonUtil.json2Object(jsonString, TransResponse.class);
			String hmacKey = TransUtil.getHMacKey(dealerInfo.getAccountPassword());
			// 根据返回内容生成报文hmac
			String responsetHmac = TransUtil.createHMac(response, hmacKey);
			// 校验报文hmac有效性
			if (responsetHmac.equals(response.getHmac())) {
				// 解密消息体
				try {
					String result = ThreeDesUtil.decryptMode(ThreeDesUtil.getKeyByte(dealerInfo.getAccountPassword()),
							String.valueOf(response.getContent()));
					response.setDecryptContent(result);
					LOGGER.info("聚享响应:" + jsonString + "|" + result);
				} catch (UnsupportedEncodingException e) {
					throw new ServiceRuntimeException("聚享数据解密异常", e);
				}
			}
		} else {
			response = new TransResponse();
		}
		return response;
	}

	private String encryptBody(Map<String, Object> contentMap) {
		// 转换为json格式字符串
		String src = JsonUtil.object2Json(contentMap);
		// 对请求内容进行3DES加密
		try {
			return ThreeDesUtil.encryptMode(dealerInfo.getAccountPassword(), src);
		} catch (UnsupportedEncodingException e) {
			throw new ServiceRuntimeException("聚享加密异常", e);
		}
	}

	/**
	 * 生成Hmac
	 */
	private String generateHmac(Map<String, Object> map) {
		// hmac密钥，平台提供的密钥的前16位作为hmac-md5算法的key，用于生成消息摘要
		String hmacKey = TransUtil.getHMacKey(dealerInfo.getAccountPassword());
		// 生成报文摘要值
		return TransUtil.createHMac(map, hmacKey);
	}

	/**
	 * 处理通知信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月19日 下午3:19:55
	 * @param requestJsonStr
	 * @return
	 */
	private NotifyRequest receiveNotify(String requestJsonStr) {
		NotifyRequest request = JsonUtil.json2Object(requestJsonStr, NotifyRequest.class);
		// 密钥，由平台生成提供给第三方，密钥长度24位16进制字符串，用作消息体加密
		String key = dealerInfo.getAccountPassword();
		// hmac密钥，平台提供的密钥的前16位作为hmac-md5算法的key，用于生成消息摘要
		String hmacKey = TransUtil.getHMacKey(key);
		// 根据返请求内容生成报文hmac
		String requestHmac = TransUtil.createHMac(request.getMap(), hmacKey);
		try {
			// 校验报文hmac有效性
			if (requestHmac.equals(request.getHmac())) {
				// 解密消息体
				String decryptData = ThreeDesUtil.decryptMode(ThreeDesUtil.getKeyByte(key),
						String.valueOf(request.getContent()));
				LOGGER.info("聚享接受通知解密消息体" + decryptData);
				request.setEncryptContent(decryptData);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("解密错误", e);
		}
		return request;
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
		// 2001|3=1.52,1=3.75,0=1.78/2005|0=3.30#2001=0
		Map<String, MatchOdds> map = new HashMap<>();
		String[] odds = odd.split("#");
		Map<String, String> rqMap = new HashMap<>();
		if (odds.length == 2) {
			String[] rqs = odds[1].split("[/=]");
			for (int i = 0; i < rqs.length; i += 2) {
				rqMap.put(rqs[i], rqs[i + 1]);
			}
		}
		String[] matchs = odds[0].split("/");
		for (String match : matchs) {
			String[] str = match.split("[|=,]");
			for (int i = 1; i < str.length; i += 2) {
				MatchOdds mOdds = new MatchOdds();
				mOdds.setOdd(str[i + 1]);
				mOdds.setTarget(rqMap.get(str[0]));
				map.put(str[0] + "_" + str[i], mOdds);
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
		//  1;9006;1;20180516|3001|9003@20,13/20180516|3002|9003@20,03/20180516|3003|9001@3/20180516|3004|9005@1,0;2001
		//  1;9006;1;20130409|2001|3,1,0/20130409|2005|0;2001
		List<MatchInfo> result = new ArrayList<>();
		String[] cis = channelContent.split("[/;]");
		for (int i = 3; i < cis.length -1; i++) {
			String[] str = cis[i].split("[|@]");
			String play = str.length == 3 ? str[2] : str[3];
			play = play.replaceAll(",", "/");
			MatchInfo mi = new MatchInfo(str[1], play);
			result.add(mi);
		}
		return result;
	}
}
