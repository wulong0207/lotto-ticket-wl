package com.hhly.ticket.service.ticket.dealer.hengpeng;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @desc 投注用户信息
 * @author jiangwei
 * @date 2017年8月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class UserProfile {
	@XStreamAsAttribute
	private String userName;
	@XStreamAsAttribute
	private String cardType;
	@XStreamAsAttribute
	private String mail;
	@XStreamAsAttribute
	private String cardNumber;
	@XStreamAsAttribute
	private String mobile;
	@XStreamAsAttribute
	private String realName;
	@XStreamAsAttribute
	private String bonusPhone;

	
	public UserProfile(String userName,String mail, String cardNumber, String mobile, String realName,
			String bonusPhone) {
		super();
		this.userName = userName;
		this.cardType = "1";
		this.mail = mail;
		this.cardNumber = cardNumber;
		this.mobile = mobile;
		this.realName = realName;
		this.bonusPhone = bonusPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBonusPhone() {
		return bonusPhone;
	}

	public void setBonusPhone(String bonusPhone) {
		this.bonusPhone = bonusPhone;
	}

}
