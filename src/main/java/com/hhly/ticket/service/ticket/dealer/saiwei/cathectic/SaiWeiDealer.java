package com.hhly.ticket.service.ticket.dealer.saiwei.cathectic;

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
import com.hhly.ticket.service.ticket.dealer.saiwei.Check;
import com.hhly.ticket.service.ticket.dealer.saiwei.NotifyTicket;
import com.hhly.ticket.service.ticket.dealer.saiwei.Odd;
import com.hhly.ticket.service.ticket.dealer.saiwei.SaiWeiUtil;
import com.hhly.ticket.service.ticket.dealer.saiwei.Ticket;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.PropertyUtil;
import com.hhly.ticket.util.TicketUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @desc 赛维出票
 * @author jiangwei
 * @date 2018年7月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SaiWeiDealer extends AbstractDealer implements INotify{

	@SuppressWarnings("unused")
	private static String USERNAME;
	private static String CARDNO;
	private static String MOBILE;
	private static String REALNAME;
	@SuppressWarnings("unused")
	private static String EMAIL;

	private  String token;
	
	private  final List<String> pass;
	
	private  double minMoney;
	
	private  long TOKEN_TIME = 0;
	//默认token刷新时间（1天）
	private  final long REFRESH_TOKEN_TIME = 1000 * 60 * 60 * 24;

	static {
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
	}

	public SaiWeiDealer(ChannelBO bo, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(bo.getDrawerAccount());
		dealerInfo.setAccountPassword(DESUtil.decrypt(bo.getAccountPassword()));
		dealerInfo.setSendUrl(bo.getSendUrl());
		dealerInfo.setSearchMaxTicket(bo.getSearchMaxTicket() > 50 ? 50 : bo.getSearchMaxTicket());
		dealerInfo.setSearchAuto(bo.getSearchAuto());
		dealerInfo.setPreBatch(bo.getPreBatch());
		super.dealerInfo = dealerInfo;
		if(bo.getStartMoney() != null){
			minMoney = bo.getStartMoney();
		}
	}

	public SaiWeiDealer(String drawerAccount, String accountPassword,String authCode, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(drawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		dealerInfo.setAuthCode(authCode);
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> tickets) {
		JSONArray arrays  = new JSONArray();
		for (int i = 0; i < tickets.size(); i++) {
			TicketBO bo = tickets.get(i);
			JSONObject json = new JSONObject();
			String no = dealerInfo.getPreBatch() + TicketUtil.getOrderNo();
			json.put("lDTicketId", no);
			json.put("lotteryTypeId", bo.getChannelLotteryCode());
			json.put("issueId", bo.getChannelLotteryIssue());
			json.put("playingCode", bo.getChannelPlayType());
			json.put("saleCode", bo.getChannelPassMode());
			json.put("userName", REALNAME);
			json.put("phone", MOBILE);
			json.put("idCard", CARDNO);
			if (bo.getLotteryCode() == 300) {
				json.put("code", bo.getChannelTicketContent().replaceAll(",", ""));
			} else {
				json.put("code", bo.getChannelTicketContent());
			}
			json.put("fee", ((int) bo.getTicketMoney()) * 100);
			json.put("timesCount", bo.getMultipleNum());
			json.put("investCount", bo.getChips() / bo.getMultipleNum());
			json.put("investType", bo.getLottoAdd());
			bo.setBatchNum(no);
			bo.setBatchNumSeq("1");
			arrays.add(json);
		}
		JSONObject object =new  JSONObject();
		object.put("tickets", arrays);
		String result = responseDealer(object, "/v2/ld/bet","post");
		JSONObject resultJson = JSONObject.fromObject(result);
		String code = resultJson.getString("code");
		if ("200".equals(code)) {
			JSONArray resultArray = resultJson.getJSONObject("data").getJSONArray("tickets");
			sendTicketSuccess(tickets, resultArray);
			return true;
		} else {
			if("10011".equals(code)){
				TOKEN_TIME = 0;
			}
			sendTicketFail(tickets, code);
			return false;
		}
	}

	/**
	 * 送票成功
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月4日 下午3:14:14
	 * @param tickets
	 * @param resultArray
	 */
	private void sendTicketSuccess(List<TicketBO> tickets, JSONArray resultArray) {
		for (int i = 0; i < resultArray.size(); i++) {
			JSONObject object = resultArray.getJSONObject(i);
			String lDTicketId = object.getString("lDTicketId");
			String result = object.getString("result");
			for (TicketBO ticketBO : tickets) {
				if (ticketBO.getBatchNum().equals(lDTicketId)) {
					// 判断出票商受理票是否成功
					if ("200002".equals(result)) {
						doTicketSuccess(ticketBO);
					} else {
						doTicketFail(ticketBO, result);
					}
					break;
				}
			}
		}

	}

	/**
	 * 发送请求
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月4日 下午3:14:26
	 * @param param
	 * @param url
	 * @return
	 */
	private String responseDealer(JSONObject object, String url,String type) {
		long now = System.currentTimeMillis();
		if ((now - TOKEN_TIME) > REFRESH_TOKEN_TIME) {
			try {
				token = SaiWeiUtil.getToken(dealerInfo.getDeawerAccount(), dealerInfo.getAccountPassword(),
						dealerInfo.getSendUrl());
				TOKEN_TIME = now;
			} catch (Exception e) {
				throw new ServiceRuntimeException("获取授权token失败", e);
			}
		}
		Map<String, String> headers = new HashMap<>();
		headers.put("Token", token);
		try {
			String json = "";
			if(object != null){
				json = object.toString();
			}
			String time = SaiWeiUtil.getTime();
			String signature = SaiWeiUtil.getSignature(json, time);
			LOGGER.info("赛维出票商请求：" + json);
			String result;
			if("post".equals(type)){
				String newUrl = dealerInfo.getSendUrl() + url +"?t="+time+"&s="+signature;
				 result = HttpUtil.doPost(newUrl, json, headers);
			}else{
				 Map<String,String> pram = new HashMap<>();
				 pram.put("t", time);
				 pram.put("s", signature);
				 result = HttpUtil.doGet(dealerInfo.getSendUrl() + url, pram, headers);
			}
			LOGGER.info("赛维出票商响应：" + result);
			return result;
		} catch (Exception e) {
			throw new ServiceRuntimeException("请求异常", e);
		}
	}

	@Override
	public double getDealerBalance() {
		String result = responseDealer(null, "/v2/ld/balance","get");
		return JSONObject.fromObject(result).getJSONObject("data").getDouble("balance")/100;
	}

	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		JSONArray array = new JSONArray();
		for (DealerCheckBO dealerCheckBO : list) {
			array.add(dealerCheckBO.getBatchNum());
		}
		JSONObject object = new JSONObject();
		object.put("ticketIds", array);
		String result = responseDealer(object, "/v2/ld/ticket/status","post");

		Map<String, Class<?>> classMap = new HashMap<>();
		classMap.put("tickets", Ticket.class);
		classMap.put("odds", Odd.class);
		return (Check) JSONObject.toBean(JSONObject.fromObject(result), Check.class, classMap);
	}
	
	@Override
	public String notifyOutTicket(String result) {
		if(StringUtils.isEmpty(result)){
			return "0";
		}
		JSONObject object  = JSONObject.fromObject(result);
		String t = object.getString("t");
		String s = object.getString("s");
		String key = Md5Util.md5_32(dealerInfo.getAuthCode() + t, "UTF-8").toUpperCase();
		if(!s.equals(key)){
			LOGGER.info("赛维出票签名验证错误："+result);
			return "0";
		}
		String tickets =object.getString("tickets");
		NotifyTicket msg = new NotifyTicket();
		Map<String, Class<?>> classMap = new HashMap<>();
		classMap.put("odds", Odd.class);
		List<Ticket> list  = JsonUtil.json2ObjectList(tickets, Ticket.class,classMap);
		msg.setTickets(list);
		handleCheckTicket(msg);
		return "1";
	}

	@Override
	public String getSuccessReuslt(String... code) {
		return null;
	}

	@Override
	protected String getRemark(String code) {
		return SaiWeiUtil.getErrorMessage(code);
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
		List<Odd> odds = JsonUtil.json2ObjectList(odd, Odd.class);
		for (Odd od : odds) {
			String key = od.getMatchNo() + "_" + od.getPlayingItem();
			MatchOdds mo = new MatchOdds();
			mo.setOdd(Double.valueOf(od.getOdds())/100+"");
			if(!StringUtils.isEmpty(od.getLetPoint())){
				mo.setTarget(Double.valueOf(od.getLetPoint())/100 +"");
			}
			map.put(key, mo);
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
		// 20101004|1|301|3,1^
		// 20101004|1|001|FT001|3,1^20101005|2|001|FT006|3,2^
		List<MatchInfo> result = new ArrayList<>();
		String[] cts = StringUtils.tokenizeToStringArray(channelContent, "^");
		for (int i = 0; i < cts.length; i++) {
			String[] ms = StringUtils.tokenizeToStringArray(cts[i], "|");
			String match = ms[2];
			String play = ms[3];
			if (ms.length == 5) {
				play = ms[4];
			}
			play = play.replaceAll(",", "/");
			MatchInfo mi = new MatchInfo(match, play);
			result.add(mi);
		}
		return result;
	}

	@Override
	public boolean isPassMode(TicketBO bo) {
		if(bo.getLotteryCode() == 300){
			return bo.getTicketMoney() >= minMoney;	
		}
		return true;
		
	}
	
}