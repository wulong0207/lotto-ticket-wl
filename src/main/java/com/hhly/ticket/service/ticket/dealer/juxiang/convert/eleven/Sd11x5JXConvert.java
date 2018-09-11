package com.hhly.ticket.service.ticket.dealer.juxiang.convert.eleven;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.AbstractConvert;
/**
 * @desc 
 * @author jiangwei
 * @date 2017年9月19日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Sd11x5JXConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "2005";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 21502:
			if(bo.getContentType() == 3){
				return "22002";
			}
			return "22001";// 山东十一选五任选二
		case 21503:
			if(bo.getContentType() == 3){
				return "22004";
			}
			return "22003";// 山东十一选五任选三
		case 21504:
			if(bo.getContentType() == 3){
				return "22006";
			}
			return "22005";// 山东十一选五任选四
		case 21505:
			if(bo.getContentType() == 3){
				return "22008";
			}
			return "22007";// 山东十一选五任选五
		case 21506:
			if(bo.getContentType() == 3){
				return "22010";
			}
			return "22009";// 山东十一选五任选六
		case 21507:
			if(bo.getContentType() == 3){
				return "22012";
			}
			return "22011";// 山东十一选五任选七
		case 21508:
			return "22013";// 山东十一选五任选八
		case 21509:
			return "22014";// 山东十一选五前一
		case 21510:
			if(bo.getContentType() == 3){
				return "22017";
			}
			return "22016";// 山东十一选五前二组选
		case 21511:
			return "22015";// 山东十一选五前二直选
		case 21512:
			if(bo.getContentType() == 3){
				return "22020";
			}
			return "22019";// 山东十一选五前三组选
		case 21513:
			return "22018";// 山东十一选五前三直选
		case 21515:
			return "22024";//乐3
		case 21516:
			return "22022";//乐4
		case 21517:
			return "22023";//乐5
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
				if("22024".equals(playType)){
					sb.append("|");
				}else{
					sb.append("|-");
				}
				break;
			case '#':
				sb.append("|*");
				break;
			case ';':
				if("22015".equals(playType) || "22018".equals(playType)){
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
		if("22015".equals(playType) || "22018".equals(playType)){
			//前2 前3 直选结尾不一样
			sb.append("|-");
		}else{
			sb.append("|");	
		}
		return sb.toString();
	}

}
