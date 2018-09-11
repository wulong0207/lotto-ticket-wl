package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 重庆快乐10分
 * @author jiangwei
 * @date 2018年1月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Cqkl10JXConvert extends AbstractConvert {

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "406";
	}
	
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue().substring(2);
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		int type = bo.getContentType();
		switch (bo.getLotteryChildCode()) {
		case 22201://前一数投
			if(type == 1){
				return "10";
			}
			return "11";			
		case 22202://前一红投
			return "12";
		case 22203://任选二
			if(type == 1){
				return "20";
			}else if(type == 2){
				return "21";
			}else{
				return "22";
			}
		case 22204://选二连组
			if(type == 1){
				return "23";
			}else if(type == 2){
				return "24";
			}else{
				return "25";
			}
		case 22205://选二连直
			return "26";
		case 22206://任选三
			if(type == 1){
				return "30";
			}else if(type == 2){
				return "31";
			}else{
				return "32";
			}
		case 22207://选三前组
			if(type == 1){
				return "33";
			}else if(type == 2){
				return "34";
			}else{
				return "35";
			}
		case 22208://选三前直
			return "36";
		case 22209://任选四
			if(type == 1){
				return "40";
			}else if(type == 2){
				return "41";
			}else{
				return "42";
			}
		case 22210://任选五
			if(type == 1){
				return "50";
			}else if(type == 2){
				return "51";
			}else{
				return "52";
			}
		default:
			throw new ServiceRuntimeException("不存在该子玩法");
		}
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder();
		for (char c : content.toCharArray()) {
			if(c == ','){
				sb.append("|");
			}else if(c == '#'){
				sb.append("|-");
			}else if(c == ';'){
				sb.append("|;");
			}else {
				sb.append(c);
			}
		}
		return sb.append("|").toString();
	}

}
