package com.hhly.ticket.service.ticket.dealer.hengpeng.request;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.hengpeng.Issue;
import com.hhly.ticket.service.ticket.dealer.hengpeng.UserProfile;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ticket")
public class CathecticTicket {
	@XStreamAsAttribute
    private String id;
	@XStreamAsAttribute
    private String playType;
	@XStreamAsAttribute
    private String money;
	@XStreamAsAttribute
    private String amount;
    
    private Issue issue;
    
    private UserProfile userProfile;
    
	@XStreamImplicit(itemFieldName="anteCode")
	private List<String> anteCode;
	

	public CathecticTicket(String id, String playType, double money, int amount, Issue issue,
			UserProfile userProfile, List<String> anteCode) {
		this.id = id;
		this.playType = playType;
		this.money = String.valueOf(money);
		this.amount = String.valueOf(amount);
		this.issue = issue;
		this.userProfile = userProfile;
		this.anteCode = anteCode;
	}

	public List<String> getAnteCode() {
		return anteCode;
	}

	public void setAnteCode(List<String> anteCode) {
		this.anteCode = anteCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	/**
	 * @return the userProfile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
	 * @param userProfile the userProfile to set
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	
}
