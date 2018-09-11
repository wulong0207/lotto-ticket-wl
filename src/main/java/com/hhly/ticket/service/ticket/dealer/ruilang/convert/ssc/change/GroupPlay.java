package com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.change;

import com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.IPlay;

/**
 * @desc 组选
 * @author jiangwei
 * @date 2017年7月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public  class GroupPlay implements IPlay {
	
	@Override
	public String simple(String content) {
		StringBuilder sb = new StringBuilder();
		for (String str : content.split(";")) {
			if(sb.length() > 0){
				sb.append("&");
			}
			sb.append(getBefore());
			sb.append(str);
		}
		return sb.toString();
	}
	@Override
	public String complex(String content) {
		return content;
	}

	@Override
	public String sum(String content) {
		return content;
	}
	/**
	 * 获取组选部位
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月11日 上午10:47:38
	 * @return
	 */
	public  String getBefore() {
		return "";
	}

}
