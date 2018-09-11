package com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.change;

import com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.IPlay;

/**
 * @desc 抽象玩法转换
 * @author jiangwei
 * @date 2017年7月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DirectPlay implements IPlay {

	@Override
	public String simple(String content) {
		return defaultPaly(content);
	}

	@Override
	public String complex(String content) {
		return defaultPaly(content);
	}

	@Override
	public String sum(String content) {
		return content;
	}
	/**
	 * 默认转换
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月11日 上午10:08:19
	 * @param content
	 * @return
	 */
	public String defaultPaly(String content){
		//单式-|-|-|-|1; _,_,_,_,5
		byte[] bytes = content.getBytes();
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			switch (b) {
			case '-':
				sb.append("_");
				break;
			case '|':
				sb.append(",");
				break;
			case ';':
				sb.append("&");
				break;
			case ',':
				sb.append(" ");
				break;
			default:
				sb.append((char)b);
				break;
			}
		}
		return sb.toString();
	}

}
