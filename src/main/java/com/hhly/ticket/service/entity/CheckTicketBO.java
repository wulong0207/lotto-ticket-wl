package com.hhly.ticket.service.entity;
/**
 * @desc 检票新
 * @author jiangwei
 * @date 2017年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CheckTicketBO {

	private String batchNum;

	private String channelId;

	private int lotteryCode;
	
	private int lotteryChildCode;

	
	public CheckTicketBO(String batchNum, String channelId, int lotteryCode,int lotteryChildCode) {
		super();
		this.batchNum = batchNum;
		this.channelId = channelId;
		this.lotteryCode = lotteryCode;
		this.lotteryChildCode = lotteryChildCode;
	}
	public CheckTicketBO() {
		
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public int getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(int lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public int getLotteryChildCode() {
		return lotteryChildCode;
	}
	public void setLotteryChildCode(int lotteryChildCode) {
		this.lotteryChildCode = lotteryChildCode;
	}
	@Override
	public String toString() {
		return "CheckTicketBO [batchNum=" + batchNum + ", channelId=" + channelId + ", lotteryCode=" + lotteryCode
				+ ", lotteryChildCode=" + lotteryChildCode + "]";
	}

	
}
