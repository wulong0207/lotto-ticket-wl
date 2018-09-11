package com.hhly.ticket.service.ticket.dealer.zhongying;

/**
 * @desc xml头
 * @author jiangwei
 * @date 2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Head {
	// 消息序号
	private String messageid;
	// 代理商ID
	private String merchant;
	// 交易码
	private String command;
	// 加密
	private String timestamp;

	private String errorcode;
	
	private String version = "1.0";

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
