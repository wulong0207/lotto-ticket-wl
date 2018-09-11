package com.hhly.ticket.service.ticket.dealer.juxiang.convert.eleven;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.AbstractConvert;
/**
 * @desc 广西11选5
 * @author jiangwei
 * @date 2018年1月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Gx11x5JXConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "2007";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 27102:
			if(bo.getContentType() == 3){
				return "24002";
			}
			return "24001";// 任选二
		case 27103:
			if(bo.getContentType() == 3){
				return "24004";
			}
			return "24003";// 任选三
		case 27104:
			if(bo.getContentType() == 3){
				return "24006";
			}
			return "24005";// 任选四
		case 27105:
			if(bo.getContentType() == 3){
				return "24008";
			}
			return "24007";// 任选五
		case 27106:
			if(bo.getContentType() == 3){
				return "24010";
			}
			return "24009";// 任选六
		case 27107:
			if(bo.getContentType() == 3){
				return "24012";
			}
			return "24011";// 任选七
		case 27108:
			return "24013";// 任选八
		case 27109:
			return "24014";// 前一
		case 27110:
			if(bo.getContentType() == 3){
				return "24017";
			}
			return "24016";// 前二组选
		case 27111:
			return "24015";// 前二直选
		case 27112:
			if(bo.getContentType() == 3){
				return "24020";
			}
			return "24019";// 前三组选
		case 27113:
			return "24018";// 前三直选
		case 27115:
			return "24024";//乐3
		case 27116:
			return "24022";//乐4
		case 21517:
			return "24023";//乐5
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		char[] chars  = bo.getTicketContent().toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			switch (c) {
			case ',':
				sb.append("|");
				break;
			case '|':
				if("24024".equals(playType)){
					sb.append("|");
				}else{
					sb.append("|-");
				}
				break;
			case '#':
				sb.append("|*");
				break;
			case ';':
				if("24015".equals(playType) || "24018".equals(playType)){
					//前2 前3 直选
					sb.append("|-;");
				}else{
					sb.append("|;");	
				}
			    break;
			default:
				sb.append(c);
				break;
			}
		}
		if("24015".equals(playType) || "24018".equals(playType)){
			//前2 前3 直选结尾不一样
			sb.append("|-");
		}else{
			sb.append("|");	
		}
		return sb.toString();
	}

}
 