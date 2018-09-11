package com.hhly.ticket.service.ticket.dealer.tengshun;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @desc
 * @author jiangwei
 * @date 2018年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Head {
	//服务编号
	@XStreamAsAttribute
	private String sid;
	//代理编号
	@XStreamAsAttribute
	private String agent;
	//消息编号
	@XStreamAsAttribute
	private String messageid;
	//时间标记
	@XStreamAsAttribute
	private String timestamp;
	//备注
	@XStreamAsAttribute
	private String memo;
	
	

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
