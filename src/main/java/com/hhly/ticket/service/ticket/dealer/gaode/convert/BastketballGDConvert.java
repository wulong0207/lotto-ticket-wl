package com.hhly.ticket.service.ticket.dealer.gaode.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
/**
 * @desc 竞彩篮球格式转换
 * @author jiangwei
 * @date 2017年10月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class BastketballGDConvert extends AbstractConvert {
	
	private static final Map<String, String> WAY_VIE;
	
	private static ISportSymbol SYMBOL = new BastballSportSymbol();
	
	static{
		WAY_VIE = new HashMap<>();
		
		WAY_VIE.put("1_1", "单关");
		WAY_VIE.put("2_1", "2x1");
		WAY_VIE.put("3_1", "3x1");
		WAY_VIE.put("4_1", "4x1");
		WAY_VIE.put("5_1", "5x1");
		WAY_VIE.put("6_1", "6x1");
		WAY_VIE.put("7_1", "7x1");
		WAY_VIE.put("8_1", "8x1");
		
		WAY_VIE.put("30101_3", "3");
		WAY_VIE.put("30101_0", "0");
		
		WAY_VIE.put("30102_3", "3");
		WAY_VIE.put("30102_0", "0");
		
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
		return null;
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "300";
	}

	@Override
	protected String getChildClass(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 30101://胜负
			return "1";
		case 30102://让分
			return "2";
		case 30103://大小分
			return "4";
		case 30104://胜分差
			return "3";
		case 30105://混
			return "5";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在子玩法");
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
				sb.append("/");
			}
			sb.append(match.substring(match.length() - 4));
			sb.append(":");
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
			return new StringBuffer(WAY_VIE.get(temp));
		}

		@Override
		public StringBuffer rightBracket(String temp, char play) {
			StringBuffer sb = comma(temp, play);
			switch (play) {
			case 'S':
				sb.append("_1");
				break;
			case 'R':
				sb.append("_2");
				break;
			case 'C':
				sb.append("_3");
				break;
			case 'D':
				sb.append("_4");
				break;
			default:
				break;
			}
			return sb;
		}
	}
}
