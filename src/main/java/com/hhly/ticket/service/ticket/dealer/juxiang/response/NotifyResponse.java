package com.hhly.ticket.service.ticket.dealer.juxiang.response;

import java.util.Map;
import java.util.TreeMap;

public class NotifyResponse {

	private String version; //版本号

	private String apiCode; // 交易号
	
	private String partnerId; // 渠道编号 

	private String messageId; // 消息流水号

	private Object content; // 加密数据
	
	private String resCode; 
	
	private String resMsg;

	private String hmac; // 报文hmac-md5,所有其他字段内容按字段名首字母序排列连接之后通过hmac-md5算法计算得到的摘要值

	private Map<String, Object> map = new TreeMap<String, Object>(); // 供hmac排序用
	
	public NotifyResponse(String apiCode,String messageId,String rescode,String channelId) {
		setVersion("1.0");
		// 需替换为平台提供的渠道商ID
		setPartnerId(channelId);
		setApiCode(apiCode);
		setMessageId(messageId);
		setResCode(rescode);
		setResMsg("");
		setContent("");
    }
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
		map.put("version", version);
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
		map.put("apiCode", apiCode);
	}
	
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
		map.put("partnerId", partnerId);
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
	
	public String getResCode() {
		return resCode;
	}
	
	public void setResCode(String resCode) {
		this.resCode = resCode;
		map.put("resCode", resCode);
	}
	
	public String getResMsg() {
		return resMsg;
	}
	
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
		map.put("resMsg", resMsg);
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
}
