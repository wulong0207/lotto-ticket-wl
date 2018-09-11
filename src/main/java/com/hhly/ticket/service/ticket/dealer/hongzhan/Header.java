package com.hhly.ticket.service.ticket.dealer.hongzhan;

public class Header {
	//代理编号
	private String agenterid;
	//消息编号
	private String messengerid;
	//时间
	private String timestamp;
	//交易类型
	private String transactiontype;
	//加密方式
	private String des;
	//加密密文
	private String digist;

	public String getAgenterid() {
		return agenterid;
	}

	public void setAgenterid(String agenterid) {
		this.agenterid = agenterid;
	}

	public String getMessengerid() {
		return messengerid;
	}

	public void setMessengerid(String messengerid) {
		this.messengerid = messengerid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getDigist() {
		return digist;
	}

	public void setDigist(String digist) {
		this.digist = digist;
	}
	
	
	
	


}
