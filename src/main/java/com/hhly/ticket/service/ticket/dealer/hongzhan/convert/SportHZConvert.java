package com.hhly.ticket.service.ticket.dealer.hongzhan.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
import com.hhly.ticket.service.ticket.dealer.SportSymbolHandler;
/**
 * 
 * @desc 鸿展
 * @author jiangwei
 * @date 2018年4月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SportHZConvert extends AbstractConvert {
	
	private static final Map<String, String> WAY_VIE;
	
	private static ISportSymbol BB_SYMBOL = new BastballSportSymbol();
	
	private static ISportSymbol FB_SYMBOL = new FootballSportSymbol();
	
	static{
		WAY_VIE = new HashMap<>();
		//足球串关
		WAY_VIE.put("300_1_1", "101");
		WAY_VIE.put("300_2_1", "102");
		WAY_VIE.put("300_3_1", "103");
		WAY_VIE.put("300_4_1", "104");
		WAY_VIE.put("300_5_1", "105");
		WAY_VIE.put("300_6_1", "106");
		WAY_VIE.put("300_7_1", "107");
		WAY_VIE.put("300_8_1", "108");

		//蓝球串关
		WAY_VIE.put("301_1_1", "02");
		WAY_VIE.put("301_2_1", "03");
		WAY_VIE.put("301_3_1", "04");
		WAY_VIE.put("301_4_1", "05");
		WAY_VIE.put("301_5_1", "06");
		WAY_VIE.put("301_6_1", "07");
		WAY_VIE.put("301_7_1", "08");
		WAY_VIE.put("301_8_1", "09");		
		
		//足球
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
		WAY_VIE.put("30101_3", "3");
		WAY_VIE.put("30101_0", "0");
		
		WAY_VIE.put("30102_3", "3");
		WAY_VIE.put("30102_0", "0");
		
		WAY_VIE.put("30103_99", "1");
		WAY_VIE.put("30103_00", "2");
		
		WAY_VIE.put("30104_01", "1");
		WAY_VIE.put("30104_02", "2");
		WAY_VIE.put("30104_03", "3");
		WAY_VIE.put("30104_04", "4");
		WAY_VIE.put("30104_05", "5");
		WAY_VIE.put("30104_06", "6");
		WAY_VIE.put("30104_11", "7");
		WAY_VIE.put("30104_12", "9");
		WAY_VIE.put("30104_13", "9");
		WAY_VIE.put("30104_14", "10");
		WAY_VIE.put("30104_15", "11");
		WAY_VIE.put("30104_16", "12");
	}

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20000";
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		//足球
		case 30001://混投
			return "208";
		case 30002://胜平负
			return "209";
		case 30003://让胜平负
			return "210";
		case 30004://比分
			return "211";
		case 30005://总进球
			return "212";
		case 30006://半全场
			return "213";
		//蓝球
		case 30101://胜负
			return "216";
		case 30102://让分
			return "214";
		case 30103://大小分
			return "215";
		case 30104://胜分差
			return "217";
		case 30105://混
			return "218";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在玩法");
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		return "0";
	}
   
	
	@Override
	public String getChannelContentType(TicketBO bo) {
		return null;
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if (str.length < 2) {
			throw new ServiceRuntimeException("格式错误");
		}
		bo.setChannelContentType(WAY_VIE.get(bo.getLotteryCode()+"_"+str[1]));
		ISportSymbol symbol = FB_SYMBOL;
		if(bo.getLotteryCode() == 301){
			symbol = BB_SYMBOL;
		}
		return SportSymbolHandler.doJcContent(str[0], bo.getLotteryChildCode(),symbol);
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
				sb.append(";");
			}
			switch (play) {
			case 'S':
				sb.append("209");
				sb.append("^");
				break;
			case 'R':
				sb.append("210");
				sb.append("^");
				break;
			case 'Q':
				sb.append("211");
				sb.append("^");
				break;
			case 'Z':
				sb.append("212");
				sb.append("^");
				break;
			case 'B':
				sb.append("213");
				sb.append("^");
				break;
			default:
				break;
			}
			sb.append(match.substring(0,6));
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
			if(playCode != null){
				temp = temp.replace("30001", playCode);
			}
			StringBuffer sb = new StringBuffer(WAY_VIE.get(temp));
			sb.append(",");
			return sb;
		}

		@Override
		public StringBuffer rightBracket(String temp, char play) {
			StringBuffer sb = comma(temp, play);
			sb.setLength(sb.length()-1);
			sb.append(")");
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
				sb.append(";");
			}
			switch (play) {
			case 'S':
				sb.append("216");
				sb.append("^");
				break;
			case 'R':
				sb.append("214");
				sb.append("^");
				break;
			case 'C':
				sb.append("217");
				sb.append("^");
				break;
			case 'D':
				sb.append("215");
				sb.append("^");
				break;
			default:
				break;
			}
			sb.append("20");
			sb.append(match.substring(0,6));
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
			sb.append(",");
			return sb;
		}

		@Override
		public StringBuffer rightBracket(String temp, char play) {
			StringBuffer sb = comma(temp, play);
			sb.setLength(sb.length()-1);
			sb.append(")");
			return sb;
		}
	}
}
