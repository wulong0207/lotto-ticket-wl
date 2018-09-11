package com.hhly.ticket.service.ticket.dealer.ruilang.request;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @desc 投注用户信息
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CathecticUserInfo {
	/**
	 * 姓名(必须)
	 */
	@XStreamAsAttribute
	private String realname;
	/**
	 * 手机号(必须)
	 */
	@XStreamAsAttribute
	private String mobile;
	/**
	 * 邮箱
	 */
	@XStreamAsAttribute
	private String email;
	/**
	 * 证件类型 1 身份证 2 护照 3 军官证
	 */
	@XStreamAsAttribute
	private String cardtype;
	
	@XStreamAsAttribute
	private String cardno;

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

}
