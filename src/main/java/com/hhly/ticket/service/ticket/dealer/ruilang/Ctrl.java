package com.hhly.ticket.service.ticket.dealer.ruilang;
/**
 * @desc 签名加密
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Ctrl {
	
	private String agentID;
	
	private String cmd;
	
	private long timestamp;
	
	private String md;

	public String getAgentID() {
		return agentID;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMd() {
		return md;
	}

	public void setMd(String md) {
		this.md = md;
	}

}
