package com.hhly.ticket.service.ticket.dealer.hongzhan.cathectic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.codec.Base64;
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
import com.hhly.ticket.service.ticket.dealer.hongzhan.CheckTicketResponse;
import com.hhly.ticket.service.ticket.dealer.hongzhan.Header;
import com.hhly.ticket.service.ticket.dealer.hongzhan.HongZhanUtil;
import com.hhly.ticket.service.ticket.dealer.hongzhan.response.CheckTicket;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.DateUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @desc 鸿展出票商
 * @author jiangwei
 * @date 2018年4月13日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class HongZhanDealer extends AbstractDealer {

	public static final String SUCCESS = "0";

	public HongZhanDealer(ChannelBO bo, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(bo.getDrawerAccount());
		dealerInfo.setAccountPassword(DESUtil.decrypt(bo.getAccountPassword()));
		dealerInfo.setSendUrl(bo.getSendUrl());
		dealerInfo.setSearchMaxTicket(bo.getSearchMaxTicket() > 100 ? 100 : bo.getSearchMaxTicket());
		dealerInfo.setSearchAuto(bo.getSearchAuto());
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> tickets) {
		JSONArray array = new JSONArray();
		tickets.forEach((ticket) -> {
			ticket.setBatchNum(TicketUtil.getOrderNo());
			ticket.setBatchNumSeq("1");
			JSONObject jo = new JSONObject();
			jo.put("id", ticket.getBatchNum());
			jo.put("lotteryid", ticket.getChannelLotteryCode());
			jo.put("issue", ticket.getChannelLotteryIssue());
			jo.put("childtype", ticket.getChannelContentType());
			jo.put("saletype", ticket.getChannelPlayType());
			jo.put("lotterycode", ticket.getChannelTicketContent());
			jo.put("appnumbers", ticket.getMultipleNum().toString());
			jo.put("lotterynumber", String.valueOf(ticket.getChips()));
			jo.put("lotteryvalue",(int)ticket.getTicketMoney() * 100);
			array.add(jo);
		});
		JSONObject r = new JSONObject();
		r.put("ticketlist", array);
		JSONObject result = request("13005", r);
		String code = result.getJSONObject("msg").getString("errorcode");
		if (SUCCESS.equals(code)) {
			sendTicketSuccess(tickets);
			return true;
		} else {
			sendTicketFail(tickets, code);
			return false;
		}
	}

	@Override
	public double getDealerBalance() {
		JSONObject msg = new JSONObject();
		msg.put("param", "test");
		JSONObject object = request("13002", msg);
		return object.getJSONObject("msg").getDouble("actmoney");
	}

	/**
	 * 请求字符串封装
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年4月13日 下午12:16:30
	 * @param transactiontype
	 * @param msg
	 * @return
	 */
	private String getRequestMsg(String transactiontype, JSONObject msg) {
		if (msg == null) {
			throw new ServiceRuntimeException("参数错误，msg不能为空");
		}
		Header header = new Header();
		JSONObject request = new JSONObject();
		String md5String = new String(Base64.encode(msg.toString().getBytes())) + dealerInfo.getAccountPassword();
		header.setDes("2");
		header.setDigist(Md5Util.md5_32(md5String, "UTF-8"));
		header.setAgenterid(dealerInfo.getDeawerAccount());
		header.setTimestamp(DateUtil.getNow(DateUtil.DATE_FORMAT_NUM));
		header.setMessengerid(TicketUtil.getOrderNo());
		header.setTransactiontype(transactiontype);

		request.put("header", header);
		request.put("msg", msg);
		return request.toString();
	}

	private JSONObject request(String transactiontype, JSONObject msg) {
		String data = getRequestMsg(transactiontype, msg);
		try {
			LOGGER.info("鸿展请求:" + data);
			String result = HttpUtil.doPostStream(dealerInfo.getSendUrl(), data, "UTF-8");
			LOGGER.info("鸿展响应:" + result);
			return JSONObject.fromObject(result);
		} catch (Exception e) {
			LOGGER.error("鸿展出票异常", e);
			throw new ServiceRuntimeException("鸿展出票请求异常", e);
		}
	}

	/**
	 * 查询彩期信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年4月13日 下午2:42:29
	 * @param code
	 * @param issue
	 */
	public void searchIssue(String code, String issue) {
		JSONObject obejct = new JSONObject();
		obejct.put("lotteryid", code);
		obejct.put("issue", issue);
		System.out.println(request("13007", obejct).toString());
	}

	@Override
	protected String getRemark(String code) {
		return HongZhanUtil.getErrorMessage(code);
	}

	@Override
	protected ICheckResponse sendCheckResponse(List<DealerCheckBO> list) {
		JSONArray array = new JSONArray();
		list.forEach((bo) -> {
			JSONObject jo = new JSONObject();
			jo.put("id", bo.getBatchNum());
			array.add(jo);
		});
		JSONObject r = new JSONObject();
		r.put("ticketlist", array);
		JSONObject result = request("13009", r);
		CheckTicketResponse response = JsonUtil.json2Object(result.getString("msg"), CheckTicketResponse.class);
		List<CheckTicket> tickets = JsonUtil.json2ObjectList(result.getJSONObject("msg").getString("ticketlist"),CheckTicket.class);
		response.setTicketlist(tickets);
		return response;
	}

	@Override
	protected String doMatchOdd(String odd, String channelContent) {
		return doMatchOddSport(odd, channelContent);
	}

	@Override
	protected Map<String, MatchOdds> getMatchOddInfo(String odd) {
		Map<String, MatchOdds> result = new HashMap<>();
		for (String single : odd.split(";")) {
			String head = "";
			if (single.indexOf("^") != -1) {
				String[] m = single.split("\\^");
				head = m[0];
				single = m[1];
			}
			String[] mi = StringUtils.tokenizeToStringArray(single, "(,)");
			for (int i = 1; i < mi.length; i++) {
				String[] str = mi[i].split("_");
				MatchOdds mo = new MatchOdds();
				if (str.length == 3) {
					mo.setOdd(str[2]);
					mo.setTarget(str[1]);
				} else {
					mo.setOdd(str[1]);
				}
				result.put(head + mi[0] + "_" + str[0], mo);
			}
		}
		return result;
	}

	@Override
	protected List<MatchInfo> getMatchInfo(String channelContent) {
		List<MatchInfo> mis = new ArrayList<>();
		for (String single : channelContent.split(";")) {
			String head = "";
			if (single.indexOf("^") != -1) {
				String[] m = single.split("\\^");
				head = m[0];
				single = m[1];
			}
			String[] str = StringUtils.tokenizeToStringArray(single, "()");
			MatchInfo mi = new MatchInfo(head + str[0], str[1].replaceAll(",", "/"));
			mis.add(mi);
		}
		return mis;
	}

}
