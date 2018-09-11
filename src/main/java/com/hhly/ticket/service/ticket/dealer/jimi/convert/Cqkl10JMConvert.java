package com.hhly.ticket.service.ticket.dealer.jimi.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 重庆快乐10分
 * @author jiangwei
 * @date 2018年4月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Cqkl10JMConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "91";
	}
	
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(2);
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 22201://前一数投
			return "1";		
		case 22202://前一红投
			return "2";
		case 22203://任选二
			return "3";
		case 22204://选二连组
			return "5";
		case 22205://选二连直
			return "4";
		case 22206://任选三
			return "6";
		case 22207://选三前组
			return "8";
		case 22208://选三前直
			return "7";
		case 22209://任选四
			return "9";
		case 22210://任选五
			return "10";
		default:
			throw new ServiceRuntimeException("不存在该子玩法");
		}
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		if(bo.getContentType() == 3){
			throw new ServiceRuntimeException("不支持胆拖");
		}
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder(playType);
		sb.append("^");
		String segmentation = "/";
		if(bo.getContentType() == 2){
			segmentation = "//";
		}
		for (char c : content.toCharArray()) {
			switch (c) {
				case ',':
				case '|':
					sb.append(segmentation);
					break;
				case ';':
					sb.append(",");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}

}
