package com.hhly.ticket.service.ticket.dealer.wencheng.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;

/**
 * @desc 竞彩
 * @author jiangwei
 * @date 2018年8月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SportWCConvert extends AbstractConvert {

	private static final Map<String, String> WAY_VIE;

	private static ISportSymbol FB_SYMBOL = new FootballSportSymbol();

	static {
		WAY_VIE = new HashMap<>();
		WAY_VIE.put("1_1", "1_1");
		WAY_VIE.put("2_1", "2_1");
		WAY_VIE.put("3_1", "3_1");
		WAY_VIE.put("4_1", "4_1");
		WAY_VIE.put("5_1", "5_1");
		WAY_VIE.put("6_1", "6_1");
		WAY_VIE.put("7_1", "7_1");
		WAY_VIE.put("8_1", "8_1");

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
	}

	@Override
	public String getPlayType(TicketBO bo) {
		return bo.getChannelPassMode();
	}

	@Override
	public String getLotteryIssye(TicketBO bo) {
		return null;
	}

	@Override
	public String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		// 足球
		case 30001:// 混投
			return "45";
		case 30002:// 胜平负
			return "40";
		case 30003:// 让胜平负
			return "41";
		case 30004:// 比分
			return "42";
		case 30005:// 总进球
			return "43";
		case 30006:// 半全场
			return "44";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在该玩法");
	}

	@Override
	public String getContent(TicketBO bo) {
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if (str.length < 2) {
			throw new ServiceRuntimeException("格式错误");
		}
		bo.setChannelPassMode(WAY_VIE.get(str[1]));
		ISportSymbol symbol = FB_SYMBOL;
		return SportSymbolHandler.doJcContent(str[0], bo.getLotteryChildCode(), symbol);
	}

	/**
	 * @desc 竞彩足球解析
	 * @author jiangwei
	 * @date 2018年8月1日
	 * @company 益彩网络科技公司
	 * @version 1.0
	 */
	private static class FootballSportSymbol implements ISportSymbol {

		@Override
		public StringBuffer leftBracket(int contentLength, String match, char play) {
			StringBuffer sb = new StringBuffer();
			if(contentLength >0){
				sb.append(";");
			}
			switch (play) {
			case 'S':
				sb.append("40:");
				break;
			case 'R':
				sb.append("41:");
				break;
			case 'Q':
				sb.append("42:");
				break;
			case 'Z':
				sb.append("43:");
				break;
			case 'B':
				sb.append("44:");
				break;
			default:
				break;
			}
			sb.append(match.substring(0, 6));
			sb.append("-");
			sb.append(match.substring(7));
			sb.append("(");
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
			sb.append(")");
			return sb;
		}
	}

}
