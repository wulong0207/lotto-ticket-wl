package com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport;

import java.util.Date;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 竞彩足球格式转换
 * @author jiangwei
 * @date 2017年5月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class FootballConvert extends AbstractSportConvert {

	@Override
	protected String getContentS(boolean single) {
		if (single) {
			return "321";
		} else {
			return "320";
		}
	}

	@Override
	protected String getContentR(boolean single) {
		if (single) {
			return "311";
		} else {
			return "301";
		}
	}
	
	@Override
	protected String getSportCode(boolean single, int lotteryChildCode) {
		switch (lotteryChildCode) {
		case 30001://混投
			return "305";
		case 30002://胜平负
			if(single){
				return "321";
			}else{
				return "320";
			}
		case 30003://让胜平负
			if(single){
				return "311";
			}else{
				return "301";
			}
		case 30004://比分
			if(single){
				return "312";
			}else{
				return "302";
			}
		case 30005://总进球
			if(single){
				return "313";
			}else{
				return "303";
			}
		case 30006://半全场
			if(single){
				return "314";
			}else{
				return "304";
			}
		default:
			break;
		}
		throw new ServiceRuntimeException("子玩法错误");
	}
	
	public static void main(String[] args) {
		FootballConvert convert = new FootballConvert();
		TicketBO bo = new TicketBO();
		bo.setBuyScreen("1611281002,1611281003,1611281004");
		bo.setMultipleNum(1);
		bo.setTicketMoney(4);
		bo.setLotteryChildCode(30001);
		// bo.setTicketContent("1611281001[+1](3@1.57,0@2.27)|1611281002[+1](1@1.89,0@4.21)|1611281003[+1](0@4.21)^3_1");
		bo.setTicketContent("1611281002_S(1@1.89,0@4.21)|1611281003_Z(0@4.21)|1611281004_R[-2](3@3.33)^3_1^10");
		bo.setStartMatchTime(new Date());
		convert.handle(bo);
		System.out.println(bo.getChannelTicketContent());
	}

}
