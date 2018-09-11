package com.hhly.ticket.service.ticket.dealer.jimi.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;

/**
 * @desc 北京单场
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class BjdcJMConvert extends AbstractConvert {
	// 北单过关方式
	private static final Map<String, String> WAY_VIE;

	private static BjdcSportSymbol SYMBOL = new BjdcSportSymbol();

	static {
		WAY_VIE = new HashMap<>();
		// *关方式
		WAY_VIE.put("1_1", "0");
		WAY_VIE.put("2_1", "2*1");
		WAY_VIE.put("3_1", "3*1");
		WAY_VIE.put("4_1", "4*1");
		WAY_VIE.put("5_1", "5*1");
		WAY_VIE.put("6_1", "6*1");
		WAY_VIE.put("7_1", "7*1");
		WAY_VIE.put("8_1", "8*1");
		WAY_VIE.put("9_1", "9*1");
		WAY_VIE.put("10_1", "10*1");
		WAY_VIE.put("11_1", "11*1");
		WAY_VIE.put("12_1", "12*1");
		WAY_VIE.put("13_1", "13*1");
		WAY_VIE.put("14_1", "14*1");
		WAY_VIE.put("15_1", "15*1");
		// 北单子玩法选好类型对应关系
		WAY_VIE.put("30601_3", "3");
		WAY_VIE.put("30601_1", "1");
		WAY_VIE.put("30601_0", "0");

		WAY_VIE.put("30602_1", "1");
		WAY_VIE.put("30602_2", "2");
		WAY_VIE.put("30602_3", "3");
		WAY_VIE.put("30602_4", "4");

		WAY_VIE.put("30603_0", "0");
		WAY_VIE.put("30603_1", "1");
		WAY_VIE.put("30603_2", "2");
		WAY_VIE.put("30603_3", "3");
		WAY_VIE.put("30603_4", "4");
		WAY_VIE.put("30603_5", "5");
		WAY_VIE.put("30603_6", "6");
		WAY_VIE.put("30603_7", "7");

		WAY_VIE.put("30604_90", "4:3");
		WAY_VIE.put("30604_10", "1:0");
		WAY_VIE.put("30604_20", "2:0");
		WAY_VIE.put("30604_21", "2:1");
		WAY_VIE.put("30604_30", "3:0");
		WAY_VIE.put("30604_31", "3:1");
		WAY_VIE.put("30604_32", "3:2");
		WAY_VIE.put("30604_40", "4:0");
		WAY_VIE.put("30604_41", "4:1");
		WAY_VIE.put("30604_42", "4:2");

		WAY_VIE.put("30604_99", "4:4");
		WAY_VIE.put("30604_00", "0:0");
		WAY_VIE.put("30604_11", "1:1");
		WAY_VIE.put("30604_22", "2:2");
		WAY_VIE.put("30604_33", "3:3");

		WAY_VIE.put("30604_09", "3:4");
		WAY_VIE.put("30604_01", "0:1");
		WAY_VIE.put("30604_02", "0:2");
		WAY_VIE.put("30604_12", "1:2");
		WAY_VIE.put("30604_03", "0:3");
		WAY_VIE.put("30604_13", "1:3");
		WAY_VIE.put("30604_23", "2:3");
		WAY_VIE.put("30604_04", "0:4");
		WAY_VIE.put("30604_14", "1:4");
		WAY_VIE.put("30604_24", "2:4");

		WAY_VIE.put("30605_33", "3_3");
		WAY_VIE.put("30605_31", "3_1");
		WAY_VIE.put("30605_30", "3_0");
		WAY_VIE.put("30605_13", "1_3");
		WAY_VIE.put("30605_11", "1_1");
		WAY_VIE.put("30605_10", "1_0");
		WAY_VIE.put("30605_03", "0_3");
		WAY_VIE.put("30605_01", "0_1");
		WAY_VIE.put("30605_00", "0_0");

		WAY_VIE.put("30701_3", "3");
		WAY_VIE.put("30701_0", "0");

	}

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(1);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 30601:// 北单让球胜平负
			return "31";
		case 30602:// 北单上下单双
			return "32";
		case 30603:// 北单总进球
			return "33";
		case 30604:// 北单比分
			return "34";
		case 30605:// 北单半全场
			return "35";
		case 30701:// 胜负过关
			return "36";
		default:
			throw new ServiceRuntimeException("不存在玩法");
		}
	}
	
	@Override
	protected String getPlayType(TicketBO bo) {
		return null;
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if (str.length < 2) {
			throw new ServiceRuntimeException("格式错误");
		}
		bo.setChannelPlayType(WAY_VIE.get(str[1]));
		return bo.getChannelPlayType() + "^" +SportSymbolHandler.doJcContent(str[0], bo.getLotteryChildCode(),SYMBOL);
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
				sb.append("//");
			}
			sb.append(match.substring(6));
			sb.append(",");
			return sb;
		}

		@Override
		public StringBuffer comma(String temp,char play) {
			return new StringBuffer(WAY_VIE.get(temp)).append("/");
		}

		@Override
		public StringBuffer rightBracket(String temp,char play) {
			return new StringBuffer(WAY_VIE.get(temp));
		}

	}

	
}
