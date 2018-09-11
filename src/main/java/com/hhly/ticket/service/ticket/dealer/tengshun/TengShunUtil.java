package com.hhly.ticket.service.ticket.dealer.tengshun;

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
public abstract class TengShunUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		private static final long serialVersionUID = -2128028321975381065L;
		{
			put("0", "成功");
			put("1000","查询无数据针对所有查询类接口");
			put("1001","代理不存在");
			put("9000","数据库错误");
			put("9001","签名不正确");
			put("9002","IP地址非法");
			put("9003","服务接口不存在");
			put("9010","调用出现异常");
		}
	};
	
	private static Map<String, String> LOTTERY_CODE = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 44377758383982509L;

		{
			put("102", "50");
			put("100", "01");
			put("107", "51");
			put("103", "52");
			put("104", "53");
			put("213", "54");
			put("215", "56");
			put("270", "60");
			put("268", "62");
			put("211", "58");
			put("210", "55");
			put("225", "57");
			put("101", "07");
			put("105", "03");
			put("237", "66");
			put("234", "67");
			put("304", "80");
			put("305", "81");
			put("303", "82");
			put("302", "83");
			put("300", "30");
			put("301", "31");
			put("308", "98");
			put("309", "99");
			put("306", "32");
			put("307", "33");
			
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
