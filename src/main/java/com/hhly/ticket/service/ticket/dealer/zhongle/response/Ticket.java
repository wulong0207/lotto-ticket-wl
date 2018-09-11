package com.hhly.ticket.service.ticket.dealer.zhongle.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Ticket {
	@XStreamAsAttribute
	private String id;
	@XStreamAsAttribute
	private String billtime;
	@XStreamAsAttribute
	private String issue;
	@XStreamAsAttribute
	private String playid;
	@XStreamAsAttribute
	private String passway;
	@XStreamAsAttribute
	private String multi;
	@XStreamAsAttribute
	private String macid;
	
    @XStreamImplicit
	private List<Match> matchs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBilltime() {
		return billtime;
	}

	public void setBilltime(String billtime) {
		this.billtime = billtime;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getPlayid() {
		return playid;
	}

	public void setPlayid(String playid) {
		this.playid = playid;
	}

	public String getPassway() {
		return passway;
	}

	public void setPassway(String passway) {
		this.passway = passway;
	}

	public String getMulti() {
		return multi;
	}

	public void setMulti(String multi) {
		this.multi = multi;
	}

	public String getMacid() {
		return macid;
	}

	public void setMacid(String macid) {
		this.macid = macid;
	}

	public List<Match> getMatchs() {
		return matchs;
	}

	public void setMatchs(List<Match> matchs) {
		this.matchs = matchs;
	}

}
