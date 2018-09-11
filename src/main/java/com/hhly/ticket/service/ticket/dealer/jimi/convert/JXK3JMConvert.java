package com.hhly.ticket.service.ticket.dealer.jimi.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 江西快3
 * @author jiangwei
 * @date 2017年12月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JXK3JMConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(2);
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "81";
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
		case 23401:
			return "1";
		case 23402:
			return "5";
		case 23403:
			return "4";
		case 23404:
			return "7";
		case 23405:
			return "6";
		case 23406:
			return "3";
		case 23407:
			return "2";
		case 23408:
			return "8";
		default:
			break;
		}
		throw new ServiceRuntimeException("玩法错误");
	
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder();
		switch (playType) {
		case "1":
		case "3":
		case "4":
		case "6":
		case "7":
			for (char c : content.toCharArray()) {
				if(c == '*' || c == ','){
					
				}else if(c == ';'){
					sb.append(",");
				}else{
					sb.append(c);
				}
			}
			content = sb.toString();
			break;
		case "2":
			content = "111";
			break;
		case "5":
			int num = 0;
			for (char c : content.toCharArray()) {
				if(c == ','){
					num ++ ;
					if(num % 2 == 0){
						sb.append("$");
					}
				}else if(c == ';'){
					sb.append(",");
				}else{
					sb.append(c);
				}
			}
			content = sb.toString();
			break;
		case "8":
			content = "123";
			break;
		default:
			break;
		}
		return playType + "^" + content;
	}

}
