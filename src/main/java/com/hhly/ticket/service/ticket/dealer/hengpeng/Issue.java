package com.hhly.ticket.service.ticket.dealer.hengpeng;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * @desc 期号查询
 * @author jiangwei
 * @date 2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Issue {
    //玩法
	@XStreamAsAttribute
	private String gameName;
	//期号
	@XStreamAsAttribute
	private String number;

	public Issue(String gameName, String number) {
		this.gameName = gameName;
		this.number = number;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
