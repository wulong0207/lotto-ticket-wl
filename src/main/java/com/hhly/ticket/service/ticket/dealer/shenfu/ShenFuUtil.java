package com.hhly.ticket.service.ticket.dealer.shenfu;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @desc
 * @author jiangwei
 * @date 2018年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class ShenFuUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 2746733063029855186L;

		{
			put("0", "操作成功");
			put("3", "暂停销售");
			put("5","查询结果不存在");
			put("6","订单已存在");
			put("10001","彩种不存在");
			put("10002","当前期不存在");
			put("10003","期状态不正确");
			put("10004","彩期不存在");
			put("10006","彩期未开售");
			put("10007","彩期无当前期");
			put("10008","已过期");
			put("10010","彩期不是当前期");
			put("10011","彩期不存在");
			put("20001","注码金额错误");
			put("20004","对阵不存在");
			put("20023","投注限号或不允许投注");
			put("9999","系统错误");
			put("4444","未知异常");
			put("30002","开奖号码不存在");
			put("40002","用户不存在");
			put("40004","用户账户不存在");
			put("40005","用户可用余额不足");
			put("40008","充值订单不存在");
			put("70001","ip错误");
			put("70002","解密错误");
			put("70003","解析错误");
			put("70004","请求命令不存在");
			put("70005","用户停用");
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
