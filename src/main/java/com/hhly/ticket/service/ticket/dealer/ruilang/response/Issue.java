package com.hhly.ticket.service.ticket.dealer.ruilang.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("issue")
public class Issue {
	@XStreamAsAttribute
	private String lotoid;
	
	@XStreamAsAttribute
	private String issue;
	
	@XStreamAsAttribute
	private String starttime;
	
	@XStreamAsAttribute
	private String endtime;
	
	@XStreamAsAttribute
	private String status;
	
	@XStreamAsAttribute
	private String bonuscode;

	public String getLotoid() {
		return lotoid;
	}

	public void setLotoid(String lotoid) {
		this.lotoid = lotoid;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the bonuscode
	 */
	public String getBonuscode() {
		return bonuscode;
	}

	/**
	 * @param bonuscode the bonuscode to set
	 */
	public void setBonuscode(String bonuscode) {
		this.bonuscode = bonuscode;
	}

}
