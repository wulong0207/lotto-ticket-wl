package com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.AbstractConvert;
/**
 * @desc 睿朗北京单场
 * @author jiangwei
 * @date 2017年9月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class BjdcConvert extends AbstractConvert {
	// 北单过关方式
	private static final Map<String, String> WAY_VIE;

	private static ISportSymbol SYMBOL = new DjdcSportSymbol();

	static {
		WAY_VIE = new HashMap<>();
		// 串关方式
		WAY_VIE.put("1_1", "21");
		WAY_VIE.put("2_1", "22");
		WAY_VIE.put("3_1", "24");
		WAY_VIE.put("4_1", "27");
		WAY_VIE.put("5_1", "31");
		WAY_VIE.put("6_1", "36");
		WAY_VIE.put("7_1", "42");
		WAY_VIE.put("8_1", "43");
		WAY_VIE.put("9_1", "44");
		WAY_VIE.put("10_1", "45");
		WAY_VIE.put("11_1", "46");
		WAY_VIE.put("12_1", "47");
		WAY_VIE.put("13_1", "48");
		WAY_VIE.put("14_1", "49");
		WAY_VIE.put("15_1", "50");
		// 北单子玩法选好类型对应关系
		WAY_VIE.put("30601_3", "胜");
		WAY_VIE.put("30601_1", "平");
		WAY_VIE.put("30601_0", "负");

		WAY_VIE.put("30602_1", "上+单");
		WAY_VIE.put("30602_2", "上+双");
		WAY_VIE.put("30602_3", "下+单");
		WAY_VIE.put("30602_4", "下+双");

		WAY_VIE.put("30603_0", "0");
		WAY_VIE.put("30603_1", "1");
		WAY_VIE.put("30603_2", "2");
		WAY_VIE.put("30603_3", "3");
		WAY_VIE.put("30603_4", "4");
		WAY_VIE.put("30603_5", "5");
		WAY_VIE.put("30603_6", "6");
		WAY_VIE.put("30603_7", "7+");

		WAY_VIE.put("30604_90", "胜其他");
		WAY_VIE.put("30604_10", "1:0");
		WAY_VIE.put("30604_20", "2:0");
		WAY_VIE.put("30604_21", "2:1");
		WAY_VIE.put("30604_30", "3:0");
		WAY_VIE.put("30604_31", "3:1");
		WAY_VIE.put("30604_32", "3:2");
		WAY_VIE.put("30604_40", "4:0");
		WAY_VIE.put("30604_41", "4:1");
		WAY_VIE.put("30604_42", "4:2");

		WAY_VIE.put("30604_99", "平其他");
		WAY_VIE.put("30604_00", "0:0");
		WAY_VIE.put("30604_11", "1:1");
		WAY_VIE.put("30604_22", "2:2");
		WAY_VIE.put("30604_33", "3:3");

		WAY_VIE.put("30604_09", "负其他");
		WAY_VIE.put("30604_01", "0:1");
		WAY_VIE.put("30604_02", "0:2");
		WAY_VIE.put("30604_12", "1:2");
		WAY_VIE.put("30604_03", "0:3");
		WAY_VIE.put("30604_13", "1:3");
		WAY_VIE.put("30604_23", "2:3");
		WAY_VIE.put("30604_04", "0:4");
		WAY_VIE.put("30604_14", "1:4");
		WAY_VIE.put("30604_24", "2:4");

		WAY_VIE.put("30605_33", "胜-胜");
		WAY_VIE.put("30605_31", "胜-平");
		WAY_VIE.put("30605_30", "胜-负");
		WAY_VIE.put("30605_13", "平-胜");
		WAY_VIE.put("30605_11", "平-平");
		WAY_VIE.put("30605_10", "平-负");
		WAY_VIE.put("30605_03", "负-胜");
		WAY_VIE.put("30605_01", "负-平");
		WAY_VIE.put("30605_00", "负-负");

		WAY_VIE.put("30701_3", "胜");
		WAY_VIE.put("30701_0", "负");

	}

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(1);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 30601:// 北单让球胜平负
			return "201";
		case 30602:// 北单上下单双
			return "202";
		case 30603:// 北单总进球
			return "203";
		case 30604:// 北单比分
			return "205";
		case 30605:// 北单半全场
			return "204";
		case 30701:// 胜负过关
			return "207";
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		// 北单的子类玩法统一为 00
		return "00";
	}

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if (str.length < 2) {
			throw new ServiceRuntimeException("北京单场格式错误");
		}
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-");
		sb.append(WAY_VIE.get(str[1]));
		sb.append("-");
		sb.append(SportSymbolHandler.doJcContent(str[0],bo.getLotteryChildCode(), SYMBOL));
		sb.append("-");
		sb.append(bo.getMultipleNum());
		sb.append("-");
		sb.append((int) bo.getTicketMoney());
		return sb.toString();
	}
    /**
     * @desc 符号处理
     * @author jiangwei
     * @date 2017年9月8日
     * @company 益彩网络科技公司
     * @version 1.0
     */
	private static class DjdcSportSymbol implements ISportSymbol {
		@Override
		public StringBuffer leftBracket(int length, String match,char play) {
			StringBuffer sb = new StringBuffer();
			if (length > 0) {
				sb.append("/");
			}
			sb.append(match.substring(6));
			sb.append(":[");
			return sb;
		}

		@Override
		public StringBuffer comma(String temp,char play) {
			StringBuffer sb = new StringBuffer();
			sb.append(WAY_VIE.get(temp));
			sb.append(",");
			return sb;
		}

		@Override
		public StringBuffer rightBracket(String temp,char play) {
			StringBuffer sb = new StringBuffer();
			sb.append(WAY_VIE.get(temp));
			sb.append("]");
			return sb;
		}

	}
}
