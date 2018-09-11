package com.hhly.ticket.service.ticket.dealer.wencheng;

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
public abstract class WenChengUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		private static final long serialVersionUID = -2128028321975381066L;
		{
			put("1001", "提交数据错误");
			put("1002", "加密验证错误");
			put("1003", "彩种不存在");
			put("1004", "余额不足");
			put("1005", "投注金额小于平台限定金额");
			put("1006", "接口不存在");
			put("1007", "彩种或者期号格式错误或者票数错误");
			put("1008", "提交票数据错误");
			put("1009", "票号重复");
			put("1010", "提交投注内容错误");
			put("1011", "系统错误");
			put("1", "收票成功");
			put("0", "收票失败");
		}
	};

	private static Map<String, String> LOTTERY_CODE = new HashMap<String, String>() {
		private static final long serialVersionUID = 130920937517683221L;

		{
			put("30030001", "45");
			put("30030002", "40");
			put("30030003", "41");
			put("30030004", "42");
			put("30030005", "43");
			put("30030006", "44");
			put("102", "21");
			put("103", "23");
			put("104", "22");
			put("107", "24");
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
		return msg == null ? "出票商不存在解释：" + code : msg;
	}
	
	public static String getCode(String code){
		String result =  LOTTERY_CODE.get(code);
		if(result == null){
			throw new ServiceRuntimeException("检票彩种错误");
		}
		return result;
	}

}
