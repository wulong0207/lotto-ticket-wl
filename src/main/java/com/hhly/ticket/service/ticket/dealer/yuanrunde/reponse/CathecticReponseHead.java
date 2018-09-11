package com.hhly.ticket.service.ticket.dealer.yuanrunde.reponse;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("head")
public class CathecticReponseHead{
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
	/**
	 * 状态码
	 */
	private String errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

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

	@Override
	public String toString() {
		return "CathecticReponseHead [msgId=" + msgId + ", commandCode=" + commandCode + ", timestamp=" + timestamp
				+ ", merchantCode=" + merchantCode + ", errorCode=" + errorCode + "]";
	}
	
	
	
}
