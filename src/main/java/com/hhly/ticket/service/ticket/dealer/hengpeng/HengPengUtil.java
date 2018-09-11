package com.hhly.ticket.service.ticket.dealer.hengpeng;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.base.exception.ServiceRuntimeException;

/**
 * @desc 工具类
 * @author jiangwei
 * @date 2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class HengPengUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		private static final long serialVersionUID = -2128028321975381065L;
		{
			put("0000", "成功,系统处理正常。 ");
			put("0010", "消息格式错误。");
			put("0011", "不支持的协议版本，比如设定了message的version属性为0.1。");
			put("0012", "messageID格式错误。");
			put("0014", "timestamp时间戳格式错误。");
			put("0015", "消息摘要不匹配。");
			put("0016", "不支持该交易类型。");
			put("0017", "MessageId重复。");
			put("0098", "单个请求超出最大并发数。");
			put("0099", "单个请求与上次时间间隔不能小于最小时间间隔。");
			put("0097", "调用委托投注接口的IP地址不是绑定的投注IP地址");
			put("1008", "玩法不存在。");
			put("1009", "奖期不存在。");
			put("1010", "处理投注过程中出现票投注失败。");
			put("1011", "奖期非投注状态。");
			put("1012", "请求消息中指定的玩法不存在。");
			put("1013", "奖期未截止。");
			put("1014", "奖期未完成期结。");
			put("1015", "奖期未完成兑奖。");
			put("1016", "代理不支持某个特定玩法（电话投注系统可以管理投注代理商支持的玩法）。");
			put("1017", "请求消息中指定的某玩法的奖期不存在。");
			put("1018", "代理商无此奖期销售统计数据。");
			put("1020", "对阵赛事非销售状态");
			put("1021", "投注订单号重复");
			put("1022", "不存在该订单号");
			put("1023", "对阵赛事不支持的投注方式");
			put("1024", "赛果不存在");
			put("1025", "对阵赛事不存或者无效的赛事编号");
			put("2001", "用户证件号码格式错误。");
			put("2002", "用户手机号码错误。");
			put("2003", "必须填写用户证件号码。");
			put("2004", "必须填写用户手机号码。");
			put("2010", "投注号码个数超出允许范围。");
			put("2011", "单个号码值超出允许范围（比如双色球投注号码中包含了78）。");
			put("2012", "禁止倍投。");
			put("2013", "禁止多期投注。");
			put("2014", "禁止胆拖投注。");
			put("2015", "禁止复式投注。");
			put("2016", "禁止组选投注。");
			put("2017", "禁止和值投注。");
			put("2018", "单票投注金额超出上限。");
			put("2030", "倍投的倍数超出范围。");
			put("2031", "多期投注的期数超出允许范围。");
			put("2032", "单个号码购买注数超出允许范围,附加信息{投注号码：剩余注数}");
			put("2040", "票金额不相符（比如ticket的money属性值与根据item计算出来的资金不符合）。");
			put("2041", "超出返奖截止时间,禁止返奖。");
			put("2042", "票流水号格式错误。");
			put("2043", "不支持的投注方式。");
			put("2044", "投注号码格式错误。");
			put("2045", "投注代理商的交易请求过于密集(系统可能对投注代理商的交易请求发送频率进行限制)");
			put("2046", "单个投注请求中包含的投注票数超出上限。");
			put("2047", "单个票查询请求中包含的投注票数超出上限。");
			put("2048", "重复发送的投注票（该投注票已经发送到电话投注系统了）。");
			put("2049", "不存在该票号。");
			put("2051", "投注失败。");
			put("2052", "投注中。");
			put("3000", "非归集帐户");
			put("3001", "投注代理商已经被冻结。");
			put("3002", "投注代理商已经被关闭。");
			put("3003", "投注代理商不存在，比如指定的messengerID非法。");
			put("3004", "投注代理商已经被暂停。");
			put("3005", "投注代理商未开启。");
			put("3010", "投注代理商销售金额已经超出上限。");
			put("3011", "投注代理商配置信息错误（管理资金账户代理商调用非管理资金账户投注接口）。");
			put("3012", "彩民帐户不存在");
			put("3013", "彩民用户资金账户可用余额不足");
			put("3014", "代理商资金账户不存在");
			put("3015", "代理商帐户余额不足");
			put("3016", "赠送彩金编号重复");
			put("3017", "单次交易赠送笔数超限");
			put("3018", "赠送彩金编号不存在");
			put("9001", "未找到支付提供商");
			put("9002", "未找到交易类型");
			put("9003", "提交参数不能为空");
			put("9004", "不支持的字符编码");
			put("9005", "支付网关返回错误");
			put("9006", "数字签名错误");
			put("9007", "交易金额不符");
			put("9008", "交易重复处理");
			put("9009", "交易不存在");
			put("9999", "系统未知异常。");
		}
	};
	// 彩种对应code
	private static Map<String, Lottery> CODE = new HashMap<String, Lottery>() {
		private static final long serialVersionUID = 7020223352026217925L;
		{
			// 低频
			put("ssq", Lottery.SSQ);
			put("3d", Lottery.F3D);
			// 高频
			put("D11", Lottery.SD11X5);
			put("HPoker", Lottery.SDPOKER);
			// 竞彩
			// 竞彩足球
			// 竞彩篮球
			// 单场
		}
	};
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
