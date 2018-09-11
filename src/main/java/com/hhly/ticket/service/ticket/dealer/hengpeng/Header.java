package com.hhly.ticket.service.ticket.dealer.hengpeng;

/**
 * @desc 出票商请求xml头
 * @author jiangwei
 * @date 2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Header {
	//出票商代理标号
    private String messengerID;
    //发送消息时的时间戳。 格式为yyyyMMddHHmmss
    private String timestamp;
    //交易类型
    private String transactionType;
    //加密
    private String digest;
    
	public String getMessengerID() {
		return messengerID;
	}
	public void setMessengerID(String messengerID) {
		this.messengerID = messengerID;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
    
    
}
