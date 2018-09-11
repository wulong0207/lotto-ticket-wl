package com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;

/**
 * @desc 竞彩篮球格式转换
 * @author jiangwei
 * @date 2017年5月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class BasketballConvert extends AbstractSportConvert {
	@Override
	protected String getContentS(boolean single) {
		if(single){
			return "317";
		}else{
			return "307";
		}
	}

	@Override
	protected String getContentR(boolean single) {
		if(single){
			return "316";
		}else{
			return "306";
		}
	}
	
	@Override
	protected String getSportCode(boolean single, int lotteryChildCode) {
		switch (lotteryChildCode) {
		case 30101://胜负
			if(single){
				return "317";
			}else{
				return "307";
			}
		case 30102://让分
			if(single){
				return "316";
			}else{
				return "306";
			}
		case 30103://大小分
			if(single){
				return "319";
			}else{
				return "309";
			}
		case 30104://胜分差
			if(single){
				return "318";
			}else{
				return "308";
			}
		case 30105://混
			return "310";
		default:
			break;
		}
		throw new ServiceRuntimeException("子玩法错误");
	}
	
	@Override
	protected String disposeContent(String content, String channelCode) {
		switch (channelCode) {
		case "306":
		case "316":
		case "307":
		case "317":
		case "309":
		case "319":
			String[] str = content.split(":");
			StringBuilder sb = new StringBuilder(str[0] );
			sb.append(":[");
			String[] tz = StringUtils.tokenizeToStringArray(str[1],"[,]");
			for (int i = 0; i < tz.length; i++) {
				if(i > 0){
					sb.append(",");
				}
				if("99".equals(tz[i]) ||
					"3".equals(tz[i])){
					sb.append("1");
				}else{
					sb.append("2");
				}
			}
			sb.append("]");
			return sb.toString();
		default:
			return content;
		}
	}

	public static void main(String[] args) {
		IConvert convert = new BasketballConvert();
		TicketBO bo = new TicketBO();
		bo.setMultipleNum(1);
		bo.setTicketMoney(4);
		bo.setStartMatchTime(new Date());
		bo.setLotteryChildCode(30103);
		bo.setBuyScreen("1611281001,1611281002,1611281003");
		bo.setTicketContent("1611281001[+1.5](0@2.27)|1611281002[+1.5](0@4.21)|1611281003[+1.5](0@4.21)^3_1^99");
		convert.handle(bo);
		System.out.println(bo.getChannelTicketContent());
		bo.setLotteryChildCode(30105);
		bo.setTicketContent("161128301_R[+11.5](3@1.57,0@2.27)|161128302_S(3@1.89,0@4.21)|161128003_D[205.5](99@4.21)|161128304_R[-2](3@3.33)^4_1^15");
		convert.handle(bo);
		System.out.println(bo.getChannelTicketContent());
	}

	
}
