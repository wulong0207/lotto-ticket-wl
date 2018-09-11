package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 江西快3
 * @author jiangwei
 * @date 2017年11月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JXK3JXConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(2);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "19";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		/*JXK3_S(23401,"和值"),
		JXK3_TD2(23402,"二同号单选"),
		JXK3_TF2(23403,"二同号复选"),
		JXK3_BT2(23404,"二不同号"),
		JXK3_BT3(23405,"三不同号"),
		JXK3_TD3(23406,"三同号单选"),
		JXK3_TT3(23407,"三同号通选"),
		JXK3_L3(23408,"三连号通选"),*/
		switch (bo.getLotteryChildCode()) {
		case 23406:
		case 23405:
		case 23402:
			return "9300";
		case 23407:
			return "9310";
		case 23403:
			return "9320";
		case 23401:
			return "9330";
		case 23404:
			return "9340";
		case 23408:
			return "9350";
		default:
			break;
		}
		throw new ServiceRuntimeException("玩法错误");
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder();
		switch (bo.getLotteryChildCode()) {
		case 23401:
			sb.append(content.replaceAll(";", "|"));
			sb.append("|");
			break;
		case 23407:
			sb.append("111|222|333|444|555|666|");
			break;
		case 23406:
			for (char code : content.toCharArray()) {
				sb.append(code);
				if(code != ';'){
					sb.append("|");
				}
			}
			break;
		case 23408:
			sb.append("123|234|345|456|");
			break;
		case 23403:
			//22*；33*；44* - >2|3|4
			char[] codes = content.toCharArray();
			for (int i = 0; i < codes.length; i+=4) {
				sb.append(codes[i]);
				sb.append("|");
			}
			break;
		case 23402:
		case 23405:
			for (char code : content.toCharArray()) {
				if(code == ','){
					sb.append("|");
				}else if(code == ';'){
					sb.append("|;");
				}else{
					sb.append(code);
				}
			}
			sb.append("|");
			break;
		case 23404:
			//1,2;1,3 -> 12|23|13|24|
			for (char code : content.toCharArray()) {
				if(code == ','){
					continue;
				}else if(code == ';'){
					sb.append("|");
				}else{
					sb.append(code);
				}
			}
			sb.append("|");
			break;
		default:
			break;
		}
		return sb.toString();
	}
}
