package com.hhly.ticket.service.ticket.dealer.zongguan;

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
public abstract class ZongGuanUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 3526878888642900759L;
		{
			put("999","系统错误（网络或某些意外而产生的错误）");
			put("901","无此用户");
			put("902","数据格式错误");
			put("903","当前没有投注期");
			put("904","数据校验失败");
			put("905","数据金额错误");
			put("906","超过倍数上限或下限");
			put("907","超过最大金额上限");
			put("908","账户余额不足");
			put("909","投注订单ID重复");
			put("910","下单过期");
			put("911","发单票数超过上限");
			put("912","方案编号过长");
			put("913","下单失败因含有取消的场次");
			put("914","未开售");
			put("915","无此种玩法");
			put("916","Xml格式错误");
			put("917","无此期号");
			put("918","发送Post参数错误");
			put("919","密钥匹配错误");
			put("920","无此交易号或者还未开通");
			put("921","玩法中包含不支持比赛场次");
			put("922","玩法不支持单式方案");
			put("923","含有已截止场次");
			put("924","暂停销售");
			put("925","存在有限制的号");
			put("926","含有未开售场次");
			put("927","此场赛程不存在");
			put("928","无此交易号");
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
