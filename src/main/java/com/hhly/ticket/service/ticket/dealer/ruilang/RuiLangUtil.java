package com.hhly.ticket.service.ticket.dealer.ruilang;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
/**
 * @desc 睿朗工具类
 * @author jiangwei
 * @date 2017年8月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class RuiLangUtil {
	// 彩种对应code
	private static Map<String, Lottery> CODE = new HashMap<String, Lottery>() {
		private static final long serialVersionUID = 7020223352026217925L;
		{
			// 低频
			put("001", Lottery.SSQ);
			put("002", Lottery.F3D);
			put("113", Lottery.DLT);
			put("108", Lottery.PL3);
			put("109", Lottery.PL5);
			put("110", Lottery.QXC);
			put("003", Lottery.QLC);
			// 高频
			put("107", Lottery.SD11X5);
			put("104", Lottery.D11X5);
			put("027", Lottery.JSK3);
			put("018", Lottery.CQSSC);
			put("024", Lottery.AHK3);
			put("103", Lottery.SDPOKER);
			put("021", Lottery.CQKL10);
			put("025", Lottery.DKL10);
			put("106", Lottery.JX11X5);
			put("102", Lottery.XJ11X5);
			put("038", Lottery.JXK3);
			// 竞彩
			put("115", Lottery.ZC6);
			put("116", Lottery.JQ4);
			put("117", Lottery.SFC);
			put("118", Lottery.ZC_NINE);
			// 竞彩足球
			put("301", Lottery.FB);
			put("311", Lottery.FB);
			put("320", Lottery.FB);
			put("321", Lottery.FB);
			put("302", Lottery.FB);
			put("312", Lottery.FB);
			put("303", Lottery.FB);
			put("313", Lottery.FB);
			put("304", Lottery.FB);
			put("314", Lottery.FB);
			put("305", Lottery.FB);
			// 竞彩篮球
			put("306", Lottery.BB);
			put("316", Lottery.BB);
			put("307", Lottery.BB);
			put("317", Lottery.BB);
			put("308", Lottery.BB);
			put("318", Lottery.BB);
			put("309", Lottery.BB);
			put("319", Lottery.BB);
			put("310", Lottery.BB);
			//单场
			put("201", Lottery.BJDC);
			put("202", Lottery.BJDC);
			put("203", Lottery.BJDC);
			put("204", Lottery.BJDC);
			put("205", Lottery.BJDC);
			put("206", Lottery.BJDC);
			put("207", Lottery.SFGG);
		}
	};

	//出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		private static final long serialVersionUID = -2128028321975381065L;
		{
			put("0","请求成功");
			put("100","请求CTRL格式不正确");
			put("101","agentID为空");
			put("102","请求的cmd为空");
			put("103","请求的cmd值不一致");
			put("104","调用的请求方法不存在");
			put("105","调用的请求方法不允许");
			put("106","请求的版本不能为空");
			put("107","请求的版本不正确");
			put("108","签名不正确");
			put("109","10秒内禁止重复发单");
			put("111","请求的方法禁用");
			put("112","agentID被禁用");
			put("200","支付失败");
			put("301","投注格式不正确或玩法不支持");
			put("302","订单投注倍数超最大限制");
			put("303","投注票金额不正确");
			put("304","投注期次过期或期次不存在");
			put("305","订单号重复");
			put("306","订单创建失败");
			put("310","最近一场开赛时间错误（竞彩）");
			put("350","查询订单不存在");
			put("360","查询奖期不存在");
			put("361","参数错误");
			put("362","未结期");
			put("400","请求参数错误或缺少");
			put("401","请求参数错误或缺少");
			put("402","IP限制");
		}
	};
	/**
	 * 获取出票商返回错误编码解释
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月1日 下午4:13:46
	 * @param code
	 * @return
	 */
	public static String getErrorMessage(String code){
		if(StringUtils.isBlank(code)){
			return code;
		}
		String msg = ERROR_MESSAGE.get(code.trim());
		return  msg == null ? "出票商不存在解释" : msg ;
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
	 * 期号格式装换 //yyyyMMdd0XX - > yyyyMMddXX
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 下午3:29:07
	 * @param issue
	 * @return
	 */
	public static String issueToXX(String issue) {
		int length = issue.length();
		return issue.substring(0, length - 3) + issue.substring(length - 2);
	}
}
