package com.hhly.ticket.service.ticket.dealer.ruilang.convert.k3;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.AbstractConvert;

/**
 * @desc 快3格式转换
 * @author jiangwei
 * @date 2017年5月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractQuickThreeConvert extends AbstractConvert {

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String content = bo.getTicketContent();
		StringBuilder sb = new StringBuilder(childClass);
		sb.append("-");
		switch (bo.getContentType()) {
		case 1:// 单
			sb.append("01-");
			switch (childClass) {
			case "02":
				content = threeEqualSimple(content);
				break;
			case "03":
				// 9,9,9 - > A,A,A
				content = "A,A,A";
				break;
			case "04":
				// 1,2,3 - > A,B,C
				content = "A,B,C";
				break;
			case "05":// 三不同号投注 只能有单式
				// 1,2,3;2,3,4 - >1,2,3&2,3,4
				content = content.replaceAll(SymbolConstants.SEMICOLON, SymbolConstants.AND);
				break;
			case "06":// 二同号单选
				content = twoEqualSimple(content);
				break;
			case "07":// 二同号复选
				content = content.substring(0, 1);
				break;
			default:
				break;
			}
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

	/**
	 * 二同号单选
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月31日 下午4:17:55
	 * @param content
	 * @return
	 */
	private String twoEqualSimple(String content) {
		// 11#2;11#3 -> 1,1,2&2,2,7
		byte[] bcs = content.getBytes();
		StringBuilder td = new StringBuilder();
		td.append((char) bcs[0]);
		for (int i = 1; i < bcs.length; i++) {
			char bc = (char) bcs[i];
			if (bc == '#') {
				continue;
			} else if (bc == ';') {
				td.append("&");
				i++;
				if (i < bcs.length) {
					td.append((char) bcs[i]);
				}
			}else{
				td.append(",");
				td.append(bc);
			}
		}
		return td.toString();
	}

	/***
	 * 三同号单选
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月31日 下午4:00:49
	 * @param content
	 * @return
	 */
	private String threeEqualSimple(String content) {
		// 111;666;222 -> 1,1,1 或 2,2,2 或 3,3,3 或 4,4,4 或 5,5,5或 6,6,6
		byte[] bcs = content.getBytes();
		StringBuilder td = new StringBuilder();
		td.append((char) bcs[0]);
		for (int i = 1; i < bcs.length; i++) {
			char bc = (char) bcs[i];
			if (bc == ';') {
				td.append("&");
				i++;
				if (i < bcs.length) {
					td.append((char) bcs[i]);
				}
			} else {
				td.append(",");
				td.append(bc);
			}
		}
		return td.toString();
	}
}
