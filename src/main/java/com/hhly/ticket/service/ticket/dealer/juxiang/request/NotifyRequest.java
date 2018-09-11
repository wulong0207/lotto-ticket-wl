package com.hhly.ticket.service.ticket.dealer.juxiang.request;

import java.util.Map;
import java.util.TreeMap;

public class NotifyRequest {

	private String apiCode; // 交易号
	
	private String partnerId; // 交易号

	private String messageId; // 消息流水号

	private Object content; // 加密数据

	private String hmac; // 报文hmac-md5
	
	private String encryptContent;
	
	private Map<String, Object> map = new TreeMap<String, Object>(); // 供hmac排序用

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
		map.put("apiCode", apiCode);
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
		map.put("content", content);
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
		map.put("messageId", messageId);
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
		map.put("partnerId", partnerId);
	}

	/**
	 * @return the encryptContent
	 */
	public String getEncryptContent() {
		return encryptContent;
	}

	/**
	 * @param encryptContent the encryptContent to set
	 */
	public void setEncryptContent(String encryptContent) {
		this.encryptContent = encryptContent;
	}
	
	
}
