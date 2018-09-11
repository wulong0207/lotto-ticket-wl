package com.hhly.ticket.service.ticket.dealer.yuanrunde.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("head")
public class CathecticHead{
	private String version = "1.0";
    /**
     * 消息序列号(商户代码+业务编码+时间戳	 (yyyyMMddHHmmss))
     */
    private String msgId;
	/**
	 * 业务编码
	 */
	private String commandCode;
	/**
	 * 发起请求的时间(yyyyMMddHHmmss)
	 */
	private String timestamp;
	/**
	 * 商户代码
	 */
	private String merchantCode;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
