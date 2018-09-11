package com.hhly.ticket.service.entity;

/**
 * @desc 出票商检票
 * @author jiangwei
 * @date 2017年8月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DealerCheckBO {
	// 彩种
	private int lotteryCode;
	// 批次号
	private String batchNum;

	private int lotteryChildCode;
	
	private String  channelId;

	public DealerCheckBO(int lotteryCode, String batchNum, int lotteryChildCode) {
		this.lotteryCode = lotteryCode;
		this.batchNum = batchNum;
		this.lotteryChildCode = lotteryChildCode;
	}
	public DealerCheckBO(){
		
	}

	public int getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(int lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	@Override
	public String toString() {
		return "DealerCheckBO [lotteryCode=" + lotteryCode + ", batchNum=" + batchNum + "]";
	}

	/**
	 * @return the lotteryChildCode
	 */
	public int getLotteryChildCode() {
		return lotteryChildCode;
	}

	/**
	 * @param lotteryChildCode
	 *            the lotteryChildCode to set
	 */
	public void setLotteryChildCode(int lotteryChildCode) {
		this.lotteryChildCode = lotteryChildCode;
	}
	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

}
