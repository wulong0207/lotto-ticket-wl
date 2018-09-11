package com.hhly.ticket.service.ticket.dealer.gaode.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;

/**
 * @desc 北京单场
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class BjdcGDConvert extends AbstractConvert {
	// 北单过关方式
	private static final Map<String, String> WAY_VIE;

	private static BjdcSportSymbol SYMBOL = new BjdcSportSymbol();

	static {
		WAY_VIE = new HashMap<>();
		// 串关方式
		WAY_VIE.put("1_1", "单关");
		WAY_VIE.put("2_1", "2串1");
		WAY_VIE.put("3_1", "3串1");
		WAY_VIE.put("4_1", "4串1");
		WAY_VIE.put("5_1", "5串1");
		WAY_VIE.put("6_1", "6串1");
		WAY_VIE.put("7_1", "7串1");
		WAY_VIE.put("8_1", "8串1");
		WAY_VIE.put("9_1", "9串1");
		WAY_VIE.put("10_1", "10串1");
		WAY_VIE.put("11_1", "11串1");
		WAY_VIE.put("12_1", "12串1");
		WAY_VIE.put("13_1", "13串1");
		WAY_VIE.put("14_1", "14串1");
		WAY_VIE.put("15_1", "15串1");
		// 北单子玩法选好类型对应关系
		WAY_VIE.put("30601_3", "3");
		WAY_VIE.put("30601_1", "1");
		WAY_VIE.put("30601_0", "0");

		WAY_VIE.put("30602_1", "3");
		WAY_VIE.put("30602_2", "4");
		WAY_VIE.put("30602_3", "1");
		WAY_VIE.put("30602_4", "2");

		WAY_VIE.put("30603_0", "0");
		WAY_VIE.put("30603_1", "1");
		WAY_VIE.put("30603_2", "2");
		WAY_VIE.put("30603_3", "3");
		WAY_VIE.put("30603_4", "4");
		WAY_VIE.put("30603_5", "5");
		WAY_VIE.put("30603_6", "6");
		WAY_VIE.put("30603_7", "7");

		WAY_VIE.put("30604_90", "90");
		WAY_VIE.put("30604_10", "10");
		WAY_VIE.put("30604_20", "20");
		WAY_VIE.put("30604_21", "21");
		WAY_VIE.put("30604_30", "30");
		WAY_VIE.put("30604_31", "31");
		WAY_VIE.put("30604_32", "32");
		WAY_VIE.put("30604_40", "40");
		WAY_VIE.put("30604_41", "41");
		WAY_VIE.put("30604_42", "42");

		WAY_VIE.put("30604_99", "99");
		WAY_VIE.put("30604_00", "00");
		WAY_VIE.put("30604_11", "11");
		WAY_VIE.put("30604_22", "22");
		WAY_VIE.put("30604_33", "33");

		WAY_VIE.put("30604_09", "09");
		WAY_VIE.put("30604_01", "01");
		WAY_VIE.put("30604_02", "02");
		WAY_VIE.put("30604_12", "12");
		WAY_VIE.put("30604_03", "03");
		WAY_VIE.put("30604_13", "13");
		WAY_VIE.put("30604_23", "23");
		WAY_VIE.put("30604_04", "04");
		WAY_VIE.put("30604_14", "14");
		WAY_VIE.put("30604_24", "24");

		WAY_VIE.put("30605_33", "33");
		WAY_VIE.put("30605_31", "31");
		WAY_VIE.put("30605_30", "30");
		WAY_VIE.put("30605_13", "13");
		WAY_VIE.put("30605_11", "11");
		WAY_VIE.put("30605_10", "10");
		WAY_VIE.put("30605_03", "03");
		WAY_VIE.put("30605_01", "01");
		WAY_VIE.put("30605_00", "00");

		WAY_VIE.put("30701_3", "3");
		WAY_VIE.put("30701_0", "0");

	}

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(1);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "100";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 30601:// 北单让球胜平负
			return "1";
		case 30602:// 北单上下单双
			return "3";
		case 30603:// 北单总进球
			return "2";
		case 30604:// 北单比分
			return "4";
		case 30605:// 北单半全场
			return "5";
		case 30701:// 胜负过关
			return "7";
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}

	@Override
	protected String getWayVie(String str) {
		return WAY_VIE.get(str);
	}

	@Override
	protected ISportSymbol getSportSymbol() {
		return SYMBOL;
	}

	/**
	 * @desc 北京单场符号处理
	 * @author jiangwei
	 * @date 2017年9月8日
	 * @company 益彩网络科技公司
	 * @version 1.0
	 */
	private static class BjdcSportSymbol implements ISportSymbol {

		@Override
		public StringBuffer leftBracket(int contentLength, String match,char play) {
			StringBuffer sb = new StringBuffer();
			if (contentLength > 0) {
				sb.append("/");
			}
			sb.append(match.substring(6));
			sb.append(":");
			return sb;
		}

		@Override
		public StringBuffer comma(String temp,char play) {
			return new StringBuffer(WAY_VIE.get(temp));
		}

		@Override
		public StringBuffer rightBracket(String temp,char play) {
			return comma(temp,play);
		}

	}
}
