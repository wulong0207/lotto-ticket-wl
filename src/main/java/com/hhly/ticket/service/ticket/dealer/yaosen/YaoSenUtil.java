package com.hhly.ticket.service.ticket.dealer.yaosen;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @desc 工具类
 * @author jiangwei
 * @date 2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class YaoSenUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		private static final long serialVersionUID = -2128028321975381065L;
		{
			put("0", "成功");
			put("1001", "参数为空");
			put("1002", "签名错误");
			put("1003", "商户不存在");
			put("1004", "商户已冻洁");
			put("1005", "提交数据过多");
			put("1006", "消息体格式错误");
			put("1007", "彩种错误");
			put("1008", "彩种已停售");
			put("1009", "无比赛信息");
			put("1010", "无在售期号");
			put("1011", "比赛已截止");
			put("1012", "有重复的比赛");
			put("1013", "超过最大倍数");
			put("1014", "超过最大金额");
			put("1015", "注数错误");
			put("1016", "金额错误");
			put("1017", "过关方式错误");
			put("1018", "余额不足");
			put("1019", "订单号重复");
			put("2001", "系统异常");
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
}
