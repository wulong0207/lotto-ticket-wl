package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 山东11选5
 * @author jiangwei
 * @date 2017年10月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Sd11X5YSConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "601";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 21502:
			return "2";// 山东十一选五任选二
		case 21503:
			return "3";// 山东十一选五任选三
		case 21504:
			return "4";// 山东十一选五任选四
		case 21505:
			return "5";// 山东十一选五任选五
		case 21506:
			return "6";// 山东十一选五任选六
		case 21507:
			return "7";// 山东十一选五任选七
		case 21508:
			return "8";// 山东十一选五任选八
		case 21509:
			return "1";// 山东十一选五前一
		case 21510:
			return "10";// 山东十一选五前二组选
		case 21511:
			return "9";// 山东十一选五前二直选
		case 21512:
			return "12";// 山东十一选五前三组选
		case 21513:
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
