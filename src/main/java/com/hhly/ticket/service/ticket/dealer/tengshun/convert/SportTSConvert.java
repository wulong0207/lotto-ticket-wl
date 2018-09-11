package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;

/**
 * @desc 滕顺
 * @author jiangwei
 * @date 2018年5月22日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SportTSConvert extends AbstractConvert {

	private static final Map<String, String> WAY_VIE;

	private static ISportSymbol FB_SYMBOL = new FootballSportSymbol();

	private static ISportSymbol BB_SYMBOL = new BastballSportSymbol();

	static {
		WAY_VIE = new HashMap<>();

		WAY_VIE.put("1_1", "1*1");
		WAY_VIE.put("2_1", "2*1");
		WAY_VIE.put("3_1", "3*1");
		WAY_VIE.put("4_1", "4*1");
		WAY_VIE.put("5_1", "5*1");
		WAY_VIE.put("6_1", "6*1");
		WAY_VIE.put("7_1", "7*1");
		WAY_VIE.put("8_1", "8*1");

		// 足球
		WAY_VIE.put("30002_3", "3");
		WAY_VIE.put("30002_1", "1");
		WAY_VIE.put("30002_0", "0");

		WAY_VIE.put("30003_3", "3");
		WAY_VIE.put("30003_1", "1");
		WAY_VIE.put("30003_0", "0");

		WAY_VIE.put("30004_90", "9:0");
		WAY_VIE.put("30004_10", "1:0");
		WAY_VIE.put("30004_20", "2:0");
		WAY_VIE.put("30004_21", "2:1");
		WAY_VIE.put("30004_30", "3:0");
		WAY_VIE.put("30004_31", "3:1");
		WAY_VIE.put("30004_32", "3:2");
		WAY_VIE.put("30004_40", "4:0");
		WAY_VIE.put("30004_41", "4:1");
		WAY_VIE.put("30004_42", "4:2");
		WAY_VIE.put("30004_50", "5:0");
		WAY_VIE.put("30004_51", "5:1");
		WAY_VIE.put("30004_52", "5:2");
		WAY_VIE.put("30004_99", "9:9");
		WAY_VIE.put("30004_00", "0:0");
		WAY_VIE.put("30004_11", "1:1");
		WAY_VIE.put("30004_22", "2:2");
		WAY_VIE.put("30004_33", "3:3");
		WAY_VIE.put("30004_09", "0:9");
		WAY_VIE.put("30004_01", "0:1");
		WAY_VIE.put("30004_02", "0:2");
		WAY_VIE.put("30004_12", "1:2");
		WAY_VIE.put("30004_03", "0:3");
		WAY_VIE.put("30004_13", "1:3");
		WAY_VIE.put("30004_23", "2:3");
		WAY_VIE.put("30004_04", "0:4");
		WAY_VIE.put("30004_14", "1:4");
		WAY_VIE.put("30004_24", "2:4");
		WAY_VIE.put("30004_05", "0:5");
		WAY_VIE.put("30004_15", "1:5");
		WAY_VIE.put("30004_25", "2:5");

		WAY_VIE.put("30005_0", "0");
		WAY_VIE.put("30005_1", "1");
		WAY_VIE.put("30005_2", "2");
		WAY_VIE.put("30005_3", "3");
		WAY_VIE.put("30005_4", "4");
		WAY_VIE.put("30005_5", "5");
		WAY_VIE.put("30005_6", "6");
		WAY_VIE.put("30005_7", "7");

		WAY_VIE.put("30006_33", "3-3");
		WAY_VIE.put("30006_31", "3-1");
		WAY_VIE.put("30006_30", "3-0");
		WAY_VIE.put("30006_13", "1-3");
		WAY_VIE.put("30006_11", "1-1");
		WAY_VIE.put("30006_10", "1-0");
		WAY_VIE.put("30006_03", "0-3");
		WAY_VIE.put("30006_01", "0-1");
		WAY_VIE.put("30006_00", "0-0");

		// 篮球
		WAY_VIE.put("30101_3", "3");
		WAY_VIE.put("30101_0", "0");

		WAY_VIE.put("30102_3", "3");
		WAY_VIE.put("30102_0", "0");

		WAY_VIE.put("30103_99", "3");
		WAY_VIE.put("30103_00", "0");

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
		return null;
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return bo.getLotteryCode() == 300 ? "30" : "31";
	}

	protected String getPlayType(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		// 足球
		case 30001:// 混投
			return "ZQHH";
		case 30002:// 胜平负
			return "SPF";
		case 30003:// 让胜平负
			return "RSPF";
		case 30004:// 比分
			return "CBF";
		case 30005:// 总进球
			return "JQS";
		case 30006:// 半全场
			return "BQC";
		// 篮球
		case 30101:// 胜负
			return "SF";
		case 30102:// 让分
			return "RFSF";
		case 30103:// 大小分
			return "DXF";
		case 30104:// 胜分差
			return "SFC";
		case 30105:// 混
			return "LQHH";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
	}

	@Override
	protected String getContent(TicketBO bo) {
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if (str.length < 2) {
			throw new ServiceRuntimeException("格式错误");
		}
		ISportSymbol symbol = FB_SYMBOL;
		if (bo.getLotteryCode() == 301) {
			symbol = BB_SYMBOL;
		}
		StringBuilder sb = new StringBuilder(getPlayType(bo));
		sb.append("|");
		sb.append(SportSymbolHandler.doJcContent(str[0], bo.getLotteryChildCode(), symbol));
		sb.append("|");
		sb.append(WAY_VIE.get(str[1]));
		return sb.toString();
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
				sb.append(",");
			}
			sb.append(match.substring(0, 6));
			sb.append(match.substring(7));
			switch (play) {
			case 'S':
				sb.append(">SPF");
				break;
			case 'R':
				sb.append(">RSPF");
				break;
			case 'Q':
				sb.append(">CBF");
				break;
			case 'Z':
				sb.append(">JQS");
				break;
			case 'B':
				sb.append(">BQC");
				break;
			default:
				break;
			}
			sb.append("=");
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
			sb.append("/");
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
				sb.append(",");
			}
			sb.append(match.substring(0, 6));
			sb.append(match.substring(7));
			switch (play) {
			case 'S':
				sb.append(">SF");
				break;
			case 'R':
				sb.append(">RFSF");
				break;
			case 'C':
				sb.append(">SFC");
				break;
			case 'D':
				sb.append(">DXF");
				break;
			default:
				break;

			}
			sb.append("=");
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
			sb.append("/");
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
