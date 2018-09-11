package com.hhly.ticket.service.ticket.dealer.hengpeng.response;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.hengpeng.Issue;
import com.hhly.ticket.service.ticket.dealer.hengpeng.UserProfile;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ticket")
public class CheckTicket extends NotifyTicket{
	@XStreamAsAttribute
	private String money;
	@XStreamAsAttribute
	private String playType;
	@XStreamAsAttribute
	private String amount;
	@XStreamAsAttribute
	private String message;

	private Issue issue;

	private UserProfile userProfile;

	@XStreamImplicit(itemFieldName = "anteCode")
	private List<String> anteCode;


	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public List<String> getAnteCode() {
		return anteCode;
	}

	public void setAnteCode(List<String> anteCode) {
		this.anteCode = anteCode;
	}

}
