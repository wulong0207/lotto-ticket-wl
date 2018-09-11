package com.hhly.ticket.service.ticket.dealer.zongguan.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ISportSymbol;
/**
 * @desc 竞彩篮球
 * @author jiangwei
 * @date 2018年3月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class FootballZGConvert extends AbstractSportConvert{
	
	private static final Map<String, String> WAY_VIE;
	
	private static ISportSymbol SYMBOL = new FootballSportSymbol();
	
	static{
		WAY_VIE = new HashMap<>();
		
		WAY_VIE.put("1_1", "P1_1");
		WAY_VIE.put("2_1", "P2_1");
		WAY_VIE.put("3_1", "P3_1");
		WAY_VIE.put("4_1", "P4_1");
		WAY_VIE.put("5_1", "P5_1");
		WAY_VIE.put("6_1", "P6_1");
		WAY_VIE.put("7_1", "P7_1");
		WAY_VIE.put("8_1", "P8_1");
		
		WAY_VIE.put("30002_3", "胜");
		WAY_VIE.put("30002_1", "平");
		WAY_VIE.put("30002_0", "负");
		
		WAY_VIE.put("30003_3", "胜");
		WAY_VIE.put("30003_1", "平");
		WAY_VIE.put("30003_0", "负");
		
		WAY_VIE.put("30004_90", "胜其他");
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
		WAY_VIE.put("30004_99", "平其他");
		WAY_VIE.put("30004_00", "0:0");
		WAY_VIE.put("30004_11", "1:1");
		WAY_VIE.put("30004_22", "2:2");
		WAY_VIE.put("30004_33", "3:3");
		WAY_VIE.put("30004_09", "负其他");
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
		WAY_VIE.put("30005_7", "7+");
		
		WAY_VIE.put("30006_33", "胜-胜");
		WAY_VIE.put("30006_31", "胜-平");
		WAY_VIE.put("30006_30", "胜-负");
		WAY_VIE.put("30006_13", "平-胜");
		WAY_VIE.put("30006_11", "平-平");
		WAY_VIE.put("30006_10", "平-负");
		WAY_VIE.put("30006_03", "负-胜");
		WAY_VIE.put("30006_01", "负-平");
		WAY_VIE.put("30006_00", "负-负");
		
	}
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue().substring(0, 4);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 30001://混投
			return "JCZQFH";
		case 30002://胜平负
			return "JCBRQSPF";
		case 30003://让胜平负
			return "JCSPF";
		case 30004://比分
			return "JCBF";
		case 30005://总进球
			return "JCJQS";
		case 30006://半全场
			return "JCBQC";
		default:
			break;
		}
		throw new ServiceRuntimeException("不存在玩法");
	
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
	 *  1611011[+1](3,0)|1611012[+1](0)|1611013(0)
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
				sb.append("/");
			}
			switch (play) {
			case 'S':
				sb.append("BRQSPF@");
				break;
			case 'R':
				sb.append("SPF@");
				break;
			case 'Q':
				sb.append("BF@");
				break;
			case 'Z':
				sb.append("JQS@");
				break;
			case 'B':
				sb.append("BQC@");
				break;
			default:
				break;
			}
			sb.append(match.substring(6,7));
			sb.append("-");
			sb.append(match.substring(7));
			sb.append(":[");
			return sb;
		}

		@Override
		public StringBuffer comma(String temp, char play) {
			return content(temp, play).append(",");
		}
		/**
		 * 内容解析
		 * @author jiangwei
		 * @Version 1.0
		 * @CreatDate 2018年3月20日 上午9:52:15
		 * @param temp
		 * @param play
		 * @return
		 */
		private StringBuffer content(String temp, char play) {
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
			return new StringBuffer(WAY_VIE.get(temp));
		}
		@Override
		public StringBuffer rightBracket(String temp, char play) {
			return content(temp, play).append("]");
		}
	}
}
