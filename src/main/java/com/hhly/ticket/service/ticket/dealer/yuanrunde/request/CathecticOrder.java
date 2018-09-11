package com.hhly.ticket.service.ticket.dealer.yuanrunde.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")
public class CathecticOrder {
	/**
	 * 销售代码
	 */
	private String saleId;
	/**
	 * 期号
	 */
	private String termNum;
	/**
	 * 投注序列号
	 */
	private String id;
	/**
	 * 玩法代码
	 */
	private String playerId;
	/**
	 * 注码
	 */
	private String betcode;
	/**
	 * 倍数
	 */
	private String multiple;
	/**
	 * 金额(单位：元)
	 */
	private String money;
	/**
	 * 追加(0否，1是)
	 */
	private String add = "0";
	/**
	 * 订单结束时间
	 */
	private String endtime;

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public String getTermNum() {
		return termNum;
	}

	public void setTermNum(String termNum) {
		this.termNum = termNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getBetcode() {
		return betcode;
	}

	public void setBetcode(String betcode) {
		this.betcode = betcode;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	
}
