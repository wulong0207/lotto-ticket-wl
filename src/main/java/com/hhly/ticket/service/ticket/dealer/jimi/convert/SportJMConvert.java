package com.hhly.ticket.service.ticket.dealer.jimi.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;
/**
 * @desc 吉米竞彩出票格式转换
 * @author jiangwei
 * @date 2018年3月30日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SportJMConvert extends AbstractConvert {
	
	private static final Map<String, String> WAY_VIE;
	
	private static ISportSymbol BB_SYMBOL = new BastballSportSymbol();
	
	private static ISportSymbol FB_SYMBOL = new FootballSportSymbol();
	
	static{
		WAY_VIE = new HashMap<>();
		WAY_VIE.put("1_1", "0");
		WAY_VIE.put("2_1", "2*1");
		WAY_VIE.put("3_1", "3*1");
		WAY_VIE.put("4_1", "4*1");
		WAY_VIE.put("5_1", "5*1");
		WAY_VIE.put("6_1", "6*1");
		WAY_VIE.put("7_1", "7*1");
		WAY_VIE.put("8_1", "8*1");
		
		//足球
		WAY_VIE.put("30002_3", "3");
		WAY_VIE.put("30002_1", "1");
		WAY_VIE.put("30002_0", "0");
		
		WAY_VIE.put("30003_3", "3");
		WAY_VIE.put("30003_1", "1");
		WAY_VIE.put("30003_0", "0");
		
		WAY_VIE.put("30004_90", "4:3");
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
		WAY_VIE.put("30004_99", "4:4");
		WAY_VIE.put("30004_00", "0:0");
		WAY_VIE.put("30004_11", "1:1");
		WAY_VIE.put("30004_22", "2:2");
		WAY_VIE.put("30004_33", "3:3");
		WAY_VIE.put("30004_09", "3:4");
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
		
		WAY_VIE.put("30006_33", "3_3");
		WAY_VIE.put("30006_31", "3_1");
		WAY_VIE.put("30006_30", "3_0");
		WAY_VIE.put("30006_13", "1_3");
		WAY_VIE.put("30006_11", "1_1");
		WAY_VIE.put("30006_10", "1_0");
		WAY_VIE.put("30006_03", "0_3");
		WAY_VIE.put("30006_01", "0_1");
		WAY_VIE.put("30006_00", "0_0");
		
		//篮球
		WAY_VIE.put("30101_3", "1");
		WAY_VIE.put("30101_0", "0");
		
		WAY_VIE.put("30102_3", "1");
		WAY_VIE.put("30102_0", "0");
		
		WAY_VIE.put("30103_99", "1");
		WAY_VIE.put("30103_00", "2");
		
		WAY_VIE.put("30104_01", "01");
		WAY_VIE.put("30104_02", "02");
		WAY_VIE.put("30104_03", "03");
		WAY_VIE.put("30104_04", "04");
		WAY_VIE.put("30104_05", "05");
		WAY_VIE.put("30104_06", "06");
		WAY_VIE.put("30104_11", "51");
		WAY_VIE.put("30104_12", "52");
		WAY_VIE.put("30104_13", "53");
		WAY_VIE.put("30104_14", "54");
		WAY_VIE.put("30104_15", "55");
		WAY_VIE.put("30104_16", "56");
	}

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "";
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		//足球
		case 30001://混投
			return "16";
		case 30002://胜平负
			return "11";
		case 30003://让胜平负
			return "15";
		case 30004://比分
			return "12";
		case 30005://总进球
			return "13";
		case 30006://半全场
			return "14";
		//蓝球
		case 30101://胜负
			return "21";
		case 30102://让分
			return "22";
		case 30103://大小分
			return "24";
		case 30104://胜分差
			return "23";
		case 30105://混
			return "25";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在玩法");
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
		ISportSymbol symbol = FB_SYMBOL;
		if(bo.getLotteryCode() == 301){
			symbol = BB_SYMBOL;
		}
		return bo.getChannelPlayType() + "^" +SportSymbolHandler.doJcContent(str[0], bo.getLotteryChildCode(),symbol);
	}
	
	/**
	 * @desc 竞彩足球
	 * @author jiangwei
	 * @date 2017年10月10日
	 * @company 益彩网络科技公司
	 * @version 1.0
	 */
	private static class FootballSportSymbol implements ISportSymbol{
		@Override
		public StringBuffer leftBracket(int contentLength, String match, char play) {
			StringBuffer sb = new StringBuffer();
			if(contentLength > 0){
				sb.append("//");
			}
			sb.append("F20");
			//1709306005 -> F20170930005
			sb.append(match.substring(0,6));
			sb.append(match.substring(7));
			sb.append(",");
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
			if(playCode != null){
				temp = temp.replace("30001", playCode);
			}
			StringBuffer sb = new StringBuffer(WAY_VIE.get(temp));
			sb.append("/");
			return sb;
		}

		@Override
		public StringBuffer rightBracket(String temp, char play) {
			StringBuffer sb = comma(temp, play);
			sb.setLength(sb.length()-1);
			switch (play) {
			case 'S':
				sb.append("-11");
				break;
			case 'R':
				sb.append("-15");
				break;
			case 'Q':
				sb.append("-12");
				break;
			case 'Z':
				sb.append("-13");
				break;
			case 'B':
				sb.append("-14");
				break;
			default:
				break;
			}
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
	private static class BastballSportSymbol implements ISportSymbol{

		@Override
		public StringBuffer leftBracket(int contentLength, String match, char play) {
			StringBuffer sb = new StringBuffer();
			if(contentLength > 0){
				sb.append("//");
			}
			sb.append("B20");
			//1709306005 -> B20170930005
			sb.append(match.substring(0,6));
			sb.append(match.substring(7));
			sb.append(",");
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
			if(playCode != null){
				temp = temp.replace("30105", playCode);
			}
			StringBuffer sb = new StringBuffer(WAY_VIE.get(temp));
			sb.append("/");
			return sb;
		}

		@Override
		public StringBuffer rightBracket(String temp, char play) {
			StringBuffer sb = comma(temp, play);
			sb.setLength(sb.length()-1);
			switch (play) {
			case 'S':
				sb.append("-21");
				break;
			case 'R':
				sb.append("-22");
				break;
			case 'C':
				sb.append("-23");
				break;
			case 'D':
				sb.append("-24");
				break;
			default:
				break;
			}
			return sb;
		}
	}
}
