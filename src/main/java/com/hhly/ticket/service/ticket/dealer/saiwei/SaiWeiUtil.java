package com.hhly.ticket.service.ticket.dealer.saiwei;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.util.Base64Util;
import com.hhly.ticket.util.HttpUtil;
import com.hhly.ticket.util.Md5Util;

import net.sf.json.JSONObject;

/**
 * 
 * @desc
 * @author jiangwei
 * @date 2018年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class SaiWeiUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		private static final long serialVersionUID = -2128028321975381065L;
		{
			put("180001", "错误（重试）");
			put("180002", "解码错误");
			put("180003", "无效参数");
			put("180004", "系统异常（重试）");
			put("180005", "无效账号");
			put("190001", "余额不足");
			put("190002", "投资格式，金额错误");
			put("190003", "投资序列号错误");
			put("190004", "无效玩法代码");
			put("190005", "玩法已封期");
			put("190006", "投资额度满");
			put("190007", "无效投资票号");
			put("190008", "无效赛事编号");
			put("190009", "不能投资单场赛事");
			put("190010", "赛事销售已经停止");
			put("190011", "非销售时间");
			put("190012", "身份信息错误，格式不对或者不满18岁");
			put("190013", "其它业务限制错误（投资金额限制，串关限制，张数限制）");

			put("200001", "出票失败");
			put("200002", "接受成功（正在出票中）");
			put("200003", "出票成功");
			put("200004", "出票失败");
			put("200005", "未开奖");
			put("200006", "未接受（正对投注接口，可以踢掉错误的票，再重投）");
			put("200007", "未中奖");
			put("200008", "中奖");
			put("10001", "System error");
			put("10002", "Service unavailable");
			put("10003", "Remote service error");
			put("10004", "IP limit");
			put("10005", "Not found this resource");
			put("10006", "No this resource config type");
			put("10007", "No this app");
			put("10008", "App user format error");
			put("10009", "Authorization expired");
			put("10010", "App password error");
			put("10011", "Token invalid");
			put("10012", "Param '%s' type error");
			put("10013", "Param '%s' is required");
			put("10014", "Param '%s' value out of enumeration");
			put("10015", "No server mapping");
			put("10016", "Background service error");
			put("10017", "Forbidden for current token");
			put("10018", "Too Many Requests");
			put("10019", "Signature verification failed");
			put("10020", "Signature invalid");
			put("10021", "Channel id is required");
			put("10022", "Channel id invalid");
			put("10023", "Refresh token error: %s");
			put("10024", "Grant type error");
			put("10025", "App expired");
			
		}
	};

	/**
	 * 获取出票商返回错误编码解释
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月1日 下午4:13:46
	 * @param code
	 * @return
	 */
	public static String getErrorMessage(String code) {
		if (StringUtils.isBlank(code)) {
			return code;
		}
		String msg = ERROR_MESSAGE.get(code.trim());
		return msg == null ? "出票商不存在解释" : msg;
	}

	/**
	 * 获取10位时间戳
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月3日 下午4:12:04
	 * @return
	 */
	public static String getTime() {
		return (new Date().getTime() + "").substring(0, 10);
	}

	/**
	 * 参数排序
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月3日 下午4:47:45
	 * @param param
	 * @return
	 */
	public static String paramSort(Map<String, String> param) {
		if (MapUtils.isEmpty(param)) {
			return "";
		}
		Object[] keys = param.keySet().toArray();
		Arrays.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (Object key : keys) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(param.get(key));
		}
		return sb.toString();
	}

	/**
	 * 获取签名Signature
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月3日 下午4:57:46
	 * @param param
	 * @param time
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSignature(String jsonStirng, String time) throws UnsupportedEncodingException {
	    //jsonStirng = URLEncoder.encode(jsonStirng, "utf-8");
		String result = Md5Util.md5_32(jsonStirng + time, "utf-8");
		return result.toUpperCase();
	}
    /**
     * 获取token
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2018年7月4日 下午12:14:00
     * @param appKey
     * @param appSecret
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
	public static String getToken(String appKey,String appSecret,String url) throws IOException, URISyntaxException {
		Map<String, String> httpParams = new HashMap<>();
		httpParams.put("app_id", appKey);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", getBasicString(appKey,appSecret,getTime()));
		String result = HttpUtil.doPost(url + "/auth", httpParams, headers);
		JSONObject json = JSONObject.fromObject(result);
		if(json.containsKey("access_token")){
			return json.getString("access_token");
		}
		throw new ServiceRuntimeException("获取token授权失败：" + result);
	}
    /**
     * 加密
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2018年7月4日 下午12:14:11
     * @param appKey
     * @param appSecret
     * @param time
     * @return
     */
	public static String getBasicString(String appKey, String appSecret, String time) {
		return "Basic " + Base64Util.encoder(appKey + "-" + time + ":" + Md5Util.md5_32(time + appSecret, "utf-8"));
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getToken("cfd088e3f36110302cf96cc51169bb82","0634ba72-baa7-3bc0-5df2-30d8c892aece","http://cai.pre.wesai.com"));
	}

}
