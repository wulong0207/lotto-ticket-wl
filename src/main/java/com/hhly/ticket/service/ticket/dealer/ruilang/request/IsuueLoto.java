package com.hhly.ticket.service.ticket.dealer.ruilang.request;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * 彩期查询
 * @desc
 * @author jiangwei
 * @date 2017年5月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class IsuueLoto {
    
	public IsuueLoto(String lotoid, String issue) {
		this.lotoid = lotoid;
		this.issue = issue;
	}
	//彩种编号
	@XStreamAsAttribute
	private String lotoid;
	//查询彩期
	//1) 当期次 issue 为空，则查询指定彩种的当前在售期次的奖期信息，
	//2) 当期次 issue 为 pre，则查询指定彩种的当前所有预售期次的奖期信息（状态为 0），
	//3) 当期次 issue 为 all，则查询指定彩种的当前所有预售与在售期次的奖期信息（状态为 0 和 1）
	@XStreamAsAttribute
	private String issue;

	public String getLotoid() {
		return lotoid;
	}

	public void setLotoid(String lotoid) {
		this.lotoid = lotoid;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

}
