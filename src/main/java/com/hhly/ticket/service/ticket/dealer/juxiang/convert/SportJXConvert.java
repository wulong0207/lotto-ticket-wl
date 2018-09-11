package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;

/**
 * @desc 竞技彩
 * @author jiangwei
 * @date 2018年5月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SportJXConvert extends AbstractConvert {

	private static final Map<String, String> WAY_VIE;

	private static ISportSymbol FB_SYMBOL = new FootballSportSymbol();

	private static ISportSymbol BB_SYMBOL = new BastballSportSymbol();

	static {
		WAY_VIE = new HashMap<>();

		WAY_VIE.put("1_1", "1001");
		WAY_VIE.put("2_1", "2001");
		WAY_VIE.put("3_1", "3001");
		WAY_VIE.put("4_1", "4001");
		WAY_VIE.put("5_1", "5001");
		WAY_VIE.put("6_1", "6001");
		WAY_VIE.put("7_1", "7001");
		WAY_VIE.put("8_1", "8001");

		// 足球
		WAY_VIE.put("30002_3", "3");
		WAY_VIE.put("30002_1", "1");
		WAY_VIE.put("30002_0", "0");

		WAY_VIE.put("30003_3", "3");
		WAY_VIE.put("30003_1", "1");
		WAY_VIE.put("30003_0", "0");

		WAY_VIE.put("30004_90", "90");
		WAY_VIE.put("30004_10", "10");
		WAY_VIE.put("30004_20", "20");
		WAY_VIE.put("30004_21", "21");
		WAY_VIE.put("30004_30", "30");
		WAY_VIE.put("30004_31", "31");
		WAY_VIE.put("30004_32", "32");
		WAY_VIE.put("30004_40", "40");
		WAY_VIE.put("30004_41", "41");
		WAY_VIE.put("30004_42", "42");
		WAY_VIE.put("30004_50", "50");
		WAY_VIE.put("30004_51", "51");
		WAY_VIE.put("30004_52", "52");
		WAY_VIE.put("30004_99", "99");
		WAY_VIE.put("30004_00", "00");
		WAY_VIE.put("30004_11", "11");
		WAY_VIE.put("30004_22", "22");
		WAY_VIE.put("30004_33", "33");
		WAY_VIE.put("30004_09", "09");
		WAY_VIE.put("30004_01", "01");
		WAY_VIE.put("30004_02", "02");
		WAY_VIE.put("30004_12", "12");
		WAY_VIE.put("30004_03", "03");
		WAY_VIE.put("30004_13", "13");
		WAY_VIE.put("30004_23", "23");
		WAY_VIE.put("30004_04", "04");
		WAY_VIE.put("30004_14", "14");
		WAY_VIE.put("30004_24", "24");
		WAY_VIE.put("30004_05", "05");
		WAY_VIE.put("30004_15", "15");
		WAY_VIE.put("30004_25", "25");

		WAY_VIE.put("30005_0", "0");
		WAY_VIE.put("30005_1", "1");
		WAY_VIE.put("30005_2", "2");
		WAY_VIE.put("30005_3", "3");
		WAY_VIE.put("30005_4", "4");
		WAY_VIE.put("30005_5", "5");
		WAY_VIE.put("30005_6", "6");
		WAY_VIE.put("30005_7", "7");

		WAY_VIE.put("30006_33", "33");
		WAY_VIE.put("30006_31", "31");
		WAY_VIE.put("30006_30", "30");
		WAY_VIE.put("30006_13", "13");
		WAY_VIE.put("30006_11", "11");
		WAY_VIE.put("30006_10", "10");
		WAY_VIE.put("30006_03", "03");
		WAY_VIE.put("30006_01", "01");
		WAY_VIE.put("30006_00", "00");

		// 篮球
		WAY_VIE.put("30101_3", "1");
		WAY_VIE.put("30101_0", "2");

		WAY_VIE.put("30102_3", "1");
		WAY_VIE.put("30102_0", "2");

		WAY_VIE.put("30103_99", "1");
		WAY_VIE.put("30103_00", "2");

		WAY_VIE.put("30104_01", "01");
		WAY_VIE.put("30104_02", "02");
		WAY_VIE.put("30104_03", "03");
		WAY_VIE.put("30104_04", "04");
		WAY_VIE.put("30104_05", "05");
		WAY_VIE.put("30104_06", "06");
		WAY_VIE.put("30104_11", "11");
		WAY_VIE.put("30104_12", "12");
		WAY_VIE.put("30104_13", "13");
		WAY_VIE.put("30104_14", "14");
		WAY_VIE.put("30104_15", "15");
		WAY_VIE.put("30104_16", "16");
	}

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return bo.getLotteryCode() == 300 ? "2010" : "2011";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		// 足球
		case 30001:// 混投
			return "9006";
		case 30002:// 胜平负
			return "9005";
		case 30003:// 让胜平负
			return "9001";
		case 30004:// 比分
			return "9003";
		case 30005:// 总进球
			return "9002";
		case 30006:// 半全场
			return "9004";
		// 篮球
		case 30101:// 胜负
			return "10001";
		case 30102:// 让分
			return "10002";
		case 30103:// 大小分
			return "10004";
		case 30104:// 胜分差
			return "10003";
		case 30105:// 混
			return "10005";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if (str.length < 2) {
			throw new ServiceRuntimeException("格式错误");
		}
		bo.setChannelPassMode(WAY_VIE.get(str[1]));
		ISportSymbol symbol = FB_SYMBOL;
		if (bo.getLotteryCode() == 301) {
			symbol = BB_SYMBOL;
		}
		return SportSymbolHandler.doJcContent(str[0], bo.getLotteryChildCode(), symbol);
	}

	/**
	 * @desc 竞彩足球
	 * @author jiangwei
	 * @date 2017年10月10日
	 * @company 益彩网络科技公司
	 * @version 1.0
	 */
	private static class FootballSportSymbol implements ISportSymbol {

		@Override
		public StringBuffer leftBracket(int contentLength, String match, char play) {
			StringBuffer sb = new StringBuffer();
			if (contentLength > 0) {
				sb.append("/");
			}
			sb.append("20");
			sb.append(match.substring(0, 6));
			sb.append("|");
			sb.append(match.substring(6));
			sb.append("|");
			switch (play) {
			case 'S':
				sb.append("9005@");
				break;
			case 'R':
				sb.append("9001@");
				break;
			case 'Q':
				sb.append("9003@");
				break;
			case 'Z':
				sb.append("9002@");
				break;
			case 'B':
				sb.append("9004@");
				break;
			default:
				break;
			}
			return sb;
		}

		@Override
		public StringBuffer comma(String temp, char play) {
			String playCode = null;
			switch (play) {
			case 'S':
				playCode = "30002";
				break;
			case 'R':
				playCode = "30003";
				break;
			case 'Q':
				playCode = "30004";
				break;
			case 'Z':
				playCode = "30005";
				break;
			case 'B':
				playCode = "30006";
				break;
			default:
				break;
			}
			if (playCode != null) {
				temp = temp.replace("30001", playCode);
			}
			StringBuffer sb = new StringBuffer(WAY_VIE.get(temp));
			sb.append(",");
			return sb;
		}

		@Override
		public StringBuffer rightBracket(String temp, char play) {
			StringBuffer sb = comma(temp, play);
			sb.setLength(sb.length() - 1);
			return sb;
		}
	}

	/**
	 * @desc 竞彩篮球
	 * @author jiangwei
	 * @date 2017年10月10日
	 * @company 益彩网络科技公司
	 * @version 1.0
	 */
	private static class BastballSportSymbol implements ISportSymbol {

		@Override
		public StringBuffer leftBracket(int contentLength, String match, char play) {
			StringBuffer sb = new StringBuffer();
			if (contentLength > 0) {
				sb.append("/");
			}
			sb.append("20");
			sb.append(match.substring(0, 6));
			sb.append("|");
			sb.append(match.substring(6));
			sb.append("|");
			switch (play) {
			case 'S':
				sb.append("10001@");
				break;
			case 'R':
				sb.append("10002@");
				break;
			case 'C':
				sb.append("10003@");
				break;
			case 'D':
				sb.append("10004@");
				break;
			default:
				break;

			}
			return sb;
		}

		@Override
		public StringBuffer comma(String temp, char play) {
			String playCode = null;
			switch (play) {
			case 'S':
				playCode = "30101";
				break;
			case 'R':
				playCode = "30102";
				break;
			case 'C':
				playCode = "30104";
				break;
			case 'D':
				playCode = "30103";
				break;
			default:
				break;
			}
			if (playCode != null) {
				temp = temp.replace("30105", playCode);
			}
			StringBuffer sb = new StringBuffer(WAY_VIE.get(temp));
			sb.append(",");
			return sb;
		}

		@Override
		public StringBuffer rightBracket(String temp, char play) {
			StringBuffer sb = comma(temp, play);
			sb.setLength(sb.length() - 1);
			return sb;
		}
	}
}
