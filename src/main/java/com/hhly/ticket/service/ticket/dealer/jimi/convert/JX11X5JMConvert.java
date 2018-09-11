package com.hhly.ticket.service.ticket.dealer.jimi.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 江西11选5
 * @author jiangwei
 * @date 2017年10月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JX11X5JMConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "73";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 21302:
			return "2";// 山东十一选五任选二
		case 21303:
			return "3";// 山东十一选五任选三
		case 21304:
			return "4";// 山东十一选五任选四
		case 21305:
			return "5";// 山东十一选五任选五
		case 21306:
			return "6";// 山东十一选五任选六
		case 21307:
			return "7";// 山东十一选五任选七
		case 21308:
			return "8";// 山东十一选五任选八
		case 21309:
			return "1";// 山东十一选五前一
		case 21310:
			return "10";// 山东十一选五前二组选
		case 21311:
			return "9";// 山东十一选五前二直选
		case 21312:
			return "12";// 山东十一选五前三组选
		case 21313:
			return "11";// 山东十一选五前三直选
		default:
			throw new ServiceRuntimeException("不存在子玩法");
		}
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		if(bo.getContentType() == 1){
			if("9".equals(playType) || "11".equals(playType)){
				content = content.replaceAll("\\|", "/");
			}else{
				content = content.replaceAll(",", "/");
			}
			if("1".equals(playType)){
				content = content.replaceAll(";", "//");
			}else{
				content = content.replaceAll(";", ",");	
			}
		}else if(bo.getContentType() == 2){
			if("9".equals(playType) || "11".equals(playType)){
				content = content.replaceAll("\\|", "//");
				content = content.replaceAll(",", "/");
			}else{
				content = content.replaceAll(",", "//");			
			}
		}else{
			content = content.replaceAll(",", "/").replace("#", "$");
		}
		return playType + "^" + content;
	}
}
