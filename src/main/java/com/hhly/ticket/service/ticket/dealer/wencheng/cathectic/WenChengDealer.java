package com.hhly.ticket.service.ticket.dealer.wencheng.cathectic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.util.CollectionUtils;
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
import com.hhly.ticket.service.ticket.dealer.wencheng.CheckResponse;
import com.hhly.ticket.service.ticket.dealer.wencheng.Ticket;
import com.hhly.ticket.service.ticket.dealer.wencheng.WenChengUtil;
import com.hhly.ticket.util.CollectionUtil;
import com.hhly.ticket.util.DESUtil;
import com.hhly.ticket.util.DateUtil;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;
import com.hhly.ticket.util.TicketUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @desc 汶澄出票
 * @author jiangwei
 * @date 2018年7月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class WenChengDealer extends AbstractDealer {

	public WenChengDealer(ChannelBO bo, IOrderService orderService) {
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

	public WenChengDealer(String drawerAccount, String accountPassword, IOrderService orderService) {
		super(orderService);
		DealerInfo dealerInfo = new DealerInfo();
		dealerInfo.setDeawerAccount(drawerAccount);
		dealerInfo.setAccountPassword(DESUtil.decrypt(accountPassword));
		super.dealerInfo = dealerInfo;
	}

	@Override
	public boolean sendTicket(List<TicketBO> ticket) {
		if (CollectionUtils.isEmpty(ticket)) {
			return true;
		}
		TicketBO first = ticket.get(0);
		JSONObject body = new JSONObject();
		body.put("lotid", Integer.parseInt(first.getChannelLotteryCode()));
		String url = "/v1/jcbet";
		if (first.getLotteryCode() != 300) {
			body.put("issue", first.getChannelLotteryIssue());
			url = "/v1/numbet";
		}
		JSONArray array = new JSONArray();
		for (TicketBO bo : ticket) {
			String order = dealerInfo.getPreBatch() + TicketUtil.getOrderNo();
			bo.setBatchNum(order);
			bo.setBatchNumSeq("1");
			JSONObject temp = new JSONObject();
			temp.put("tscn", order);
			temp.put("money", (int) bo.getTicketMoney());
			temp.put("multiple", bo.getMultipleNum());

			if (first.getLotteryCode() == 300) {
				temp.put("playtype", bo.getChannelPlayType());
			} else {
				temp.put("playtype", Integer.parseInt(bo.getChannelPlayType()));
				temp.put("lottype", Integer.parseInt(bo.getChannelContentType()));
			}
			temp.put("lotconn", bo.getChannelTicketContent());
			array.add(temp);
		}
		body.put("ticket", array);
		String result = responseDealer(body, url);
		JSONObject resultJson = JSONObject.fromObject(result).getJSONObject("body");
		String code = resultJson.getString("errorcode");
		if ("1000".equals(code)) {
			JSONArray resultArray = resultJson.getJSONArray("data");
			sendTicketSuccess(ticket, resultArray);
			return true;
		} else {
			sendTicketFail(ticket, code);
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
			String tscn = object.getString("tscn");
			String status = object.getString("status");
			for (TicketBO ticketBO : tickets) {
				if (ticketBO.getBatchNum().equals(tscn)) {
					// 判断出票商受理票是否成功
					if ("1".equals(status)) {
						String outscn = object.getString("outscn");
						ticketBO.setThirdNum(outscn);
						doTicketSuccess(ticketBO);
					} else {
						doTicketFail(ticketBO, status);
					}
					break;
				}
			}
		}

	}

	@Override
	public double getDealerBalance() {
		JSONObject body = new JSONObject();
		body.put("agent", dealerInfo.getDeawerAccount());
		String result = responseDealer(body, "/v1/balance");
		return JSONObject.fromObject(result).getJSONObject("body").getJSONObject("data").getDouble("money")/100;
	}

	/**
	 * 检票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年8月1日 下午5:41:33
	 * @param dealerCheckBOs
	 * @return
	 */
	private List<String> checkResponse(List<DealerCheckBO> dealerCheckBOs) {
		Map<String, List<String>> check = new HashMap<>();
		for (DealerCheckBO dealerCheckBO : dealerCheckBOs) {
			String key = dealerCheckBO.getLotteryCode() + "";
			if (dealerCheckBO.getLotteryCode() == 300) {
				key += dealerCheckBO.getLotteryChildCode();
			}
			String lotid = WenChengUtil.getCode(key);
			List<String> list = check.get(lotid);
			if (list == null) {
				list = new ArrayList<>();
				check.put(lotid, list);
			}
			list.add(dealerCheckBO.getBatchNum());
		}
		List<String> resultList = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : check.entrySet()) {
			JSONObject body = new JSONObject();
			body.put("lotid", Integer.parseInt(entry.getKey()));
			JSONArray array = new JSONArray();
			for (String tscn : entry.getValue()) {
				array.add(tscn);
			}
			body.put("tscn", array);
			String result = responseDealer(body, "/v1/ticket");
			JSONObject resultbody = JSONObject.fromObject(result).getJSONObject("body");
			Map<String, Class<?>> classMap = new HashMap<>();
			classMap.put("data", Ticket.class);
			CheckResponse cr = (CheckResponse) JSONObject.toBean(resultbody, CheckResponse.class, classMap);
			resultList.addAll(handleCheckTicket(cr));
		}
		return resultList;
	}

	@Override
	public List<String> checkTicket(List<DealerCheckBO> checkBO) {
		List<String> result = new ArrayList<>();
		if (CollectionUtils.isEmpty(checkBO)) {
			return result;
		}
		List<List<DealerCheckBO>> subList = CollectionUtil.subList(checkBO, dealerInfo.getSearchMaxTicket());
		for (List<DealerCheckBO> list : subList) {
			result.addAll(checkResponse(list));
		}
		return result;
	}

	/**
	 * 发送请求
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月31日 下午4:29:15
	 * @param body
	 * @param url
	 * @return
	 */
	private String responseDealer(JSONObject body, String url) {
		JSONObject param = new JSONObject();
		param.put("body", body);
		param.put("header", getHeader(body));
		try {
			String p = param.toString();
			LOGGER.info("汶澄出票商请求：" + p);
			String result = HttpUtil.doPost(dealerInfo.getSendUrl() + url, p);
			LOGGER.info("汶澄出票商响应：" + result);
			return result;
		} catch (Exception e) {
			throw new ServiceRuntimeException("", e);
		}
	}

	/**
	 * 获取请求头部
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月31日 下午4:23:41
	 * @param body
	 * @return
	 */
	private JSONObject getHeader(JSONObject body) {
		String time = DateUtil.getNow(DateUtil.DATE_FORMAT_NUM);
		String momo = UUID.randomUUID().toString();
		StringBuilder sb = new StringBuilder();
		sb.append("agent=");
		sb.append(dealerInfo.getDeawerAccount());
		sb.append("&timestamp=");
		sb.append(time);
		sb.append("&memo=");
		sb.append(momo);
		sb.append("&body=");
		sb.append(body.toString());
		sb.append("&key=");
		sb.append(dealerInfo.getAccountPassword());
		JSONObject object = new JSONObject();
		object.put("agent", dealerInfo.getDeawerAccount());
		object.put("timestamp", time);
		object.put("memo", momo);
		object.put("key", Md5Util.md5_32(sb.toString(), "UTF-8"));
		return object;
	}

	@Override
	protected String getRemark(String code) {
		return WenChengUtil.getErrorMessage(code);
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
	 *            180628-047(1_6.89);180628-048(1_8.33)
	 *            40:180704-101(3_8.66,1_2.19,0_8.32);41:180704-102(3_7.78,1_8.
	 *            75,0_9.20)
	 * @return key:场次编号（混合投注+玩法）+投注内容 普通 ：1001_3 混合：1001_1_3 value:赔率
	 */
	@Override
	protected Map<String, MatchOdds> getMatchOddInfo(String odd) {
		Map<String, MatchOdds> map = new HashMap<>();
		String[] matchs = odd.split(";");
		for (String match : matchs) {
			String[] m = StringUtils.tokenizeToStringArray(match, "(,)");
			for (int i = 1; i < m.length; i++) {
				String[] str = m[i].split("_");
				String key = m[0] + "_" + str[0];
				MatchOdds mo = new MatchOdds();
				mo.setOdd(str[1]);
				map.put(key, mo);
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
		// 17904-004(3);17904-005(1,0) ;17904-005(3,1,0)
		// 17904-004(3);17904-005(1,0) ;17904-005(3,1,0)
		List<MatchInfo> result = new ArrayList<>();
		String[] cts = StringUtils.tokenizeToStringArray(channelContent, ";");
		for (int i = 0; i < cts.length; i++) {
			String[] ms = StringUtils.tokenizeToStringArray(cts[i], "()");
			String match = ms[0];
			String play = ms[1].replaceAll(",", "/");
			MatchInfo mi = new MatchInfo(match, play);
			result.add(mi);
		}
		return result;
	}

	@Override
	public boolean isPassMode(TicketBO bo) {
		double num = bo.getTicketMoney() / 2 / bo.getMultipleNum();
		if (bo.getLotteryCode() < 300 && bo.getContentType() == 1 && num > 1) {
			return false;
		}
		return true;
	}
}
