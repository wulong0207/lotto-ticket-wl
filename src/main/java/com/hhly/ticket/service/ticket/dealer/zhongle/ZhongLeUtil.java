package com.hhly.ticket.service.ticket.dealer.zhongle;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.exception.ServiceRuntimeException;

/**
 * 
 * @desc
 * @author jiangwei
 * @date 2018年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class ZhongLeUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 2746733063029855186L;

		{
			put("0", "功，一般是指投注成功，在1001接口中使用。                        ");
			put("1","功，出票成功后返回，在2001查询接口中使用");
			put("ES001","参数错误。");
			put("ES002","签名验证失败。");
			put("ES003","无效请求。");
			put("ES004","商户账户已冻结或不存在。");
			put("ES098","系统忙。");
			put("EA001","玩法代码错误，不存在或已停售。");
			put("EA002","期号错误，不存在或已过期。");
			put("EA003","商户帐户余额不足。");
			put("EA004","商户账户已冻结或不存在。");
			put("EA005","投注格式错误。");
			put("EA006","投注金额与投注内容不符。");
			put("EA007","系统限号，投注失败。");
			put("EA008","订单号以存在，投注失败。");
			put("EA009","订单号格式错误。");
			put("EA010","交易参数包数据错误。");
			put("EA012","投注倍数超过最大倍数限制。");
			put("EA013","单次投注注数超过规定限额。");
			put("EA014","某场次比赛未开售或投注玩法未开售。");
			put("EA015","单注金额超过2万限制");
			put("EA016","投注的场次号不存在或已销售截止");
			put("EA017","收票截止或当前时间到投注比赛的开赛时间之间机器不开售");
			put("EQ001","订单号不存在。");
			put("EQ002","订单还在出票中。");
			put("EQ003","出票失败订单取消。");
			put("EQ004","查询失败。");
			put("EQ005","票号为空。");
			put("EQ006","结算中。");
			put("ES901","系统错误导致数据保存失败。");
			put("ES999","系统未知异常。");
			put("ES901","系统错误导致数据保存失败。");
			put("ES902","调用彩票平台的IP地址不是代理商绑定的投注IP地址");
			put("ES903","系统处理超时投注失败【超过24000毫秒的投注处理】");
			put("ES999","系统未知异常。");
		}
	};
	
	private static Map<String, String> LOTTERY_CODE = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 44377758383982509L;

		{
			put("300", "10042");
			put("301", "10043");
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
	 * 转换成出票商彩种
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月18日 下午2:59:33
	 * @param lotteryCode
	 * @return
	 */
	public static String getChannelLotteryCode(int lotteryCode){
		String  code = LOTTERY_CODE.get(String.valueOf(lotteryCode));
		if(StringUtils.isBlank(code)){
			throw new ServiceRuntimeException("彩种编号转换异常");
		}
		return code; 
	}
	
}
