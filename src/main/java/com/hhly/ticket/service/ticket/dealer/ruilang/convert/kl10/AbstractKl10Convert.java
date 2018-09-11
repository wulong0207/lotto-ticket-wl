package com.hhly.ticket.service.ticket.dealer.ruilang.convert.kl10;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.AbstractConvert;
/**
 * @desc 快乐10 格式装换
 * @author jiangwei
 * @date 2017年8月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractKl10Convert extends AbstractConvert {

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-");
		// 1：单式；2：复式
		switch (bo.getContentType()) {
		case 1:
			sb.append("01-");
			if("02".equals(childClass)){
				//选1红投
				String [] str =  content.split(";");
				content = "19,20";
				for (int i = 1; i < str.length; i++) {
					content += "&19,20";
				}
			}else{
				//直选
				if("04".equals(childClass)||"09".equals(childClass)){
					content = content.replaceAll(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR, SymbolConstants.COMMA);
				}
				content = content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND);	
			}
			break;
		case 2:
			sb.append("02-");
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

}
