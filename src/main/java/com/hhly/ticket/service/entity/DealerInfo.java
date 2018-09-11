package com.hhly.ticket.service.entity;
/**
 * @desc 渠道出票商基本信息
 * @author jiangwei
 * @date 2017年9月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DealerInfo {
	// 是否主动查票
	private int searchAuto;
	// 账号
	private String deawerAccount;
	// 密码
	private String accountPassword;
	// 送票url
	private String sendUrl;
	// 最大送票张数
	private int searchMaxTicket;

	private String preBatch;
	//认证码
	private String authCode;

	public int getSearchAuto() {
		return searchAuto;
	}

	public void setSearchAuto(int searchAuto) {
		this.searchAuto = searchAuto;
	}

	public String getDeawerAccount() {
		return deawerAccount;
	}

	public void setDeawerAccount(String deawerAccount) {
		this.deawerAccount = deawerAccount;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public int getSearchMaxTicket() {
		return searchMaxTicket;
	}

	public void setSearchMaxTicket(int searchMaxTicket) {
		this.searchMaxTicket = searchMaxTicket;
	}

	public String getPreBatch() {
		return preBatch;
	}

	public void setPreBatch(String preBatch) {
		this.preBatch = preBatch;
	}

	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

}
