package com.hhly.ticket.service.ticket.dealer.juxiang.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.base.exception.ServiceRuntimeException;

/**
 * @desc 工具类
 * @author jiangwei
 * @date 2017年9月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class JuXiangUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>();
	static{
		ERROR_MESSAGE.put("0", "成功 ");
		ERROR_MESSAGE.put("100000","交易失败，检查修改hmac的生成后重新请求");
		ERROR_MESSAGE.put("100001","交易失败，调用查票接口确认投注状态后再决定是否重新请求");
		ERROR_MESSAGE.put("100002","交易失败，检查修改加密算法后重新请求");
		ERROR_MESSAGE.put("100003","交易失败，修改参数后重新请求");
		ERROR_MESSAGE.put("20000","稍后尝试重新请求");
		ERROR_MESSAGE.put("20036","交易失败，订单数量超出限制");
		ERROR_MESSAGE.put("20002","交易失败，服务异常，尝试重新请求");
		ERROR_MESSAGE.put("20003","交易失败，调用查票接口确认投注状态后再决定是否重新请求");
		ERROR_MESSAGE.put("20004","交易失败，检查游戏参数后重新请求");
		ERROR_MESSAGE.put("20005","交易失败，修改期号后重新请求");
		ERROR_MESSAGE.put("20007","交易失败，尝试重新请求");
		ERROR_MESSAGE.put("20010","交易失败，尝试重新请求");
		ERROR_MESSAGE.put("20012","交易失败，请联系运营人员开通权限后重新请求");
		ERROR_MESSAGE.put("20013","交易失败，尝试重新请求");
		ERROR_MESSAGE.put("20014","交易失败，请联系运营人员处理");
		ERROR_MESSAGE.put("20015","交易失败，尝试重新请求");
		ERROR_MESSAGE.put("20016","交易失败，尝试重新请求");
		ERROR_MESSAGE.put("20017","交易失败，尝试重新请求");
		ERROR_MESSAGE.put("20018","交易失败，尝试重新请求");
		ERROR_MESSAGE.put("20019","交易失败，尝试重新请求");
		ERROR_MESSAGE.put("20020","涵盖各种业务错误，例如投注号码格式错误、号码个数错误等等，具体请参看错误信息");
		ERROR_MESSAGE.put("20024","交易失败，修改订单号后重新请求");
		ERROR_MESSAGE.put("20025","投注失败，修改订单号后重新投注");
		ERROR_MESSAGE.put("20026","当前游戏玩法超过最大投注注数，热线系统不再予以出票");
		ERROR_MESSAGE.put("20033","投注失败，请联系运营人员充值后重新投注");
		ERROR_MESSAGE.put("20034","投注失败，修改请求参数后重新投注");
		ERROR_MESSAGE.put("20030","交易失败，请更换期号重新请求");
		ERROR_MESSAGE.put("20031","交易失败，稍后尝试重新请");
		ERROR_MESSAGE.put("20032","交易失败，当前期停止预售");
		ERROR_MESSAGE.put("20035","交易失败，修改游戏类型后重新请求");
		ERROR_MESSAGE.put("20037","请重试");
	}
	// 彩种对应code
	private static Map<String, Lottery> CODE = new HashMap<String, Lottery>();
	static{
					// 低频
					CODE.put("4",Lottery.SSQ);
					CODE.put("7",Lottery.QLC);
					// 高频
					CODE.put("2005",Lottery.SD11X5);
					CODE.put("2007",Lottery.GX11X5);
					CODE.put("19",Lottery.JXK3);
					CODE.put("406",Lottery.CQKL10);
					// 竞彩
					// 竞彩足球
					// 竞彩篮球
					// 单场
	}
	/**
	 * 出票商商彩种转换本系统彩种
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月19日 上午10:31:53
	 * @param lotoid
	 * @return
	 */
	public static Lottery getLotteryCode(String lotoid) {
		Lottery lottery = CODE.get(lotoid);
		if (lottery == null) {
			throw new ServiceRuntimeException("出票商彩种在本系统不存在");
		}
		return lottery;
	}

	/**
	 * 本系统彩种转换为供应商彩种 只能获取数字彩和老足彩 竞彩足球和竞彩篮球不能调用改方法
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 下午2:21:11
	 * @param lottery
	 * @return
	 */
	public static String getCode(Lottery lottery) {
		for (Map.Entry<String, Lottery> entry : CODE.entrySet()) {
			if (entry.getValue() == lottery) {
				return entry.getKey();
			}
		}
		throw new ServiceRuntimeException(lottery.getDesc() + "，出票商不存在该彩种");
	}
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
