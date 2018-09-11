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
public class BasketballZGConvert extends AbstractSportConvert{
	
	private static final Map<String, String> WAY_VIE;
	
	private static ISportSymbol SYMBOL = new BastballSportSymbol();
	
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
		
		WAY_VIE.put("30101_3", "主胜");
		WAY_VIE.put("30101_0", "客胜");
		
		WAY_VIE.put("30102_3", "主胜");
		WAY_VIE.put("30102_0", "客胜");
		
		WAY_VIE.put("30103_99", "大");
		WAY_VIE.put("30103_00", "小");
		
		WAY_VIE.put("30104_01", "胜1-5");
		WAY_VIE.put("30104_02", "胜6-10");
		WAY_VIE.put("30104_03", "胜11-15");
		WAY_VIE.put("30104_04", "胜16-20");
		WAY_VIE.put("30104_05", "胜21-25");
		WAY_VIE.put("30104_06", "胜26+");
		WAY_VIE.put("30104_11", "负1-5");
		WAY_VIE.put("30104_12", "负6-10");
		WAY_VIE.put("30104_13", "负11-15");
		WAY_VIE.put("30104_14", "负16-20");
		WAY_VIE.put("30104_15", "负21-25");
		WAY_VIE.put("30104_16", "负26+");
		
	}
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue().substring(0, 4);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 30101://胜负
			return "JCSF";
		case 30102://让分
			return "JCRFSF";
		case 30103://大小分
			return "JCDXF";
		case 30104://胜分差
			return "JCFC";
		case 30105://混
			return "JCLQFH";
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
	private static class BastballSportSymbol implements ISportSymbol{

		@Override
		public StringBuffer leftBracket(int contentLength, String match, char play) {
			StringBuffer sb = new StringBuffer();
			if(contentLength > 0){
				sb.append("/");
			}
			switch (play) {
			case 'S':
				sb.append("SF@");
				break;
			case 'R':
				sb.append("RFSF@");
				break;
			case 'C':
				sb.append("FC@");
				break;
			case 'D':
				sb.append("DXF@");
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
		 * 投注内容解析
		 * @author jiangwei
		 * @Version 1.0
		 * @CreatDate 2018年3月20日 上午9:50:41
		 * @param temp
		 * @param play
		 * @return
		 */
		private StringBuffer content(String temp, char play) {
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
			return content(temp, play).append("]");
		}
	}
}
