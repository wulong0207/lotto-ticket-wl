package com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.change;

import com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.IPlay;

/**
 * @desc 大小单双
 * @author jiangwei
 * @date 2017年7月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SizePlay implements IPlay {

	@Override
	public String simple(String content) {
		StringBuilder sb = new StringBuilder();
		for (byte b : content.getBytes()) {
			if(b == ';'){
				sb.append("&");
			}else if(b == '3'){
				sb.append("5");
			}else if(b == '|'){
				sb.append(",");
			}else{
				sb.append((char)b);
			}
		}
		return sb.toString();
	}

	@Override
	public String complex(String content) {
		return null;
	}

	@Override
	public String sum(String content) {
		return null;
	}

}
