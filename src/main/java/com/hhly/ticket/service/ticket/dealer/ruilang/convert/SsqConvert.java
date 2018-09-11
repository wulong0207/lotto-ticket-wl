package com.hhly.ticket.service.ticket.dealer.ruilang.convert;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 双色球
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SsqConvert extends AbstractConvert {
	@Override
	protected String getChildClass(TicketBO bo) {
		return "00";
	}
	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "001";
	}
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}
	 /**
     * 本系统
     *  单：01,02,03,04,05,06+01;01,02,03,04,05,06+02 ->01,02,03,04,05,06#01&01,02,03,04,05,06#02
     *  复：01,02,03,04,05,06,07,08+01,02,03 ->01,02,03,04,05,06,07,08#01,02,03
     *  胆拖：01,02,03#04,05,06,07+02 -> 01,02,03,@04,05,06,07#01,02
     * @see 子类-选号方式-投注号 1&投注号 2-倍数-金额
     * 00-01-01,02,03,05,07,08#01&01,02,03,05,07,12#02-1-4
     */
	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-");
		//1：单式；2：复式；3：胆拖；
		switch (bo.getContentType()) {
		case 1:
			content = content.replaceAll(SymbolConstants.ADD_DOUBLE_SLASH, SymbolConstants.NUMBER_SIGN);
			content = content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND);
			sb.append("01-");
			break;
		case 2:
			content = content.replaceAll(SymbolConstants.ADD_DOUBLE_SLASH, SymbolConstants.NUMBER_SIGN);
			sb.append("02-");
			break;
		case 3:
			content = content.replaceAll(SymbolConstants.NUMBER_SIGN, SymbolConstants.AT);
			content = content.replaceAll(SymbolConstants.ADD_DOUBLE_SLASH, SymbolConstants.NUMBER_SIGN);
			sb.append("03-");
			
			break;
		default:
			throw new ServiceRuntimeException("不存在内容类型");
		}
		sb.append(content);
		sb.append("-");
		sb.append(bo.getMultipleNum());
		sb.append("-");
		sb.append((int) bo.getTicketMoney());
		return sb.toString();
	}
   

	public static void main(String[] args) {
		//单
		SsqConvert ssqConvert = new SsqConvert();
		TicketBO bo = new TicketBO();
		bo.setContentType(1);
		bo.setMultipleNum(1);
		bo.setTicketMoney(4);
		bo.setTicketContent("01,02,03,04,05,06+01;01,02,03,04,05,06+02");
		ssqConvert.handle(bo);
		System.out.println(bo.getChannelTicketContent());
		//复
		TicketBO bo2 = new TicketBO();
		bo2.setContentType(2);
		bo2.setMultipleNum(1);
		bo2.setTicketMoney(168);
		bo2.setTicketContent("01,02,03,04,05,06,07,08+01,02,03");
		ssqConvert.handle(bo2);
		System.out.println(bo2.getChannelTicketContent());
		//胆
		TicketBO bo3 = new TicketBO();
		bo3.setContentType(3);
		bo3.setMultipleNum(1);
		bo3.setTicketMoney(8);
		bo3.setTicketContent("01,02,03#04,05,06,07+02");
		ssqConvert.handle(bo3);
		System.out.println(bo3.getChannelTicketContent());
	}
	
}
