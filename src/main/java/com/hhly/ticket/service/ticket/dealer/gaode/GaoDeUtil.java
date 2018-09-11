package com.hhly.ticket.service.ticket.dealer.gaode;

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
public abstract class GaoDeUtil {
	// 出票商返回错误编码代表信息
	private static Map<String, String> ERROR_MESSAGE = new HashMap<String, String>() {
		private static final long serialVersionUID = -2128028321975381065L;
		{
			put("0", "请求成功。");
			put("1", "XML格式错误。");
			put("2", "消息摘要验证失败。");
			put("3", "用户名无效。");
			put("4", "操作码无效。");
			put("5", "XML中缺少节点或节点值无效。");
			put("6", "系统未知异常。请稍后重试。");
			put("7", "单位时间内请求次数太多，将暂停服务。请减少单位时间内的请求次数。");
			put("8", "单位时间内投注请求次数太多，将暂停投注服务。请减少单位时间内的投注请求次数。");
			put("9", "待出票数量已经达到上限，将暂停接受投注。请稍后再投注。");
			put("-1", "未知异常。请稍后重新投注。");
			put("-2", "重复投注。说明该票之前已经投注成功。");
			put("1007", "账号被锁定，无法投注。请联系商务人员了解被锁定的原因。");
			put("2001", "商品期号不能为空。");
			put("2003", "无效的彩种ID。");
			put("2004", "无效的玩法ID。");
			put("3004", "倍数不能小于等于0或者大于99。");
			put("3005", "金额不能小于等于0或者大于2万。投注金额与注数、倍速计算不符。");
			put("3021", "注数不能小于等于0或者大于1万。注数与投注内容、过关方式计算不匹配。");
			put("3027", "一次投注或查询的票数量不能超过{上限值}。");
			put("3208", "单倍注数不能超过10000注。");
			put("6001", "未知的彩种或玩法。");
			put("6002", "票列表不能为空。票ID不能为空。票ID不能重复。投注内容投注内容格式错误。");
			put("6003", "投注比赛数量超出彩种和玩法规定。投注的赛事数量超过混合过关限制。");
			put("6004", "过关方式为空或无法识别。彩种玩法不支持该过关方式。混合过关不支持单关。");
			put("6006", "投注内容场次数量与过关方式不匹配。");
			put("7001", "订单投注的商品不存在。");
			put("7002", "订单投注的商品不在销售中或不在销售时间段。");
			put("7003", "投注的比赛不存在。单场投注的比赛尚未开售。");
			put("7004", "投注的比赛不在销售中。");
			put("7005", "投注的比赛已过停售时刻。");
			put("7007", "投注的比赛处于暂停销售状态。");
			put("7008", "投注的赛事的玩法不在销售中。");
			put("8001", "账户余额扣除失败（可能是账户余额不足）。");
			put("15120", "竞彩投注的某场比赛的某个玩法不支持单关投注。");
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
