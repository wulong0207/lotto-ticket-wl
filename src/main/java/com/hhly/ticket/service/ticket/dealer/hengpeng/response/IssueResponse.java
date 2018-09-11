package com.hhly.ticket.service.ticket.dealer.hengpeng.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
public class IssueResponse {
	
	@XStreamAsAttribute
    private String code;
	
	
	@XStreamAsAttribute
    private String message;
	
	@XStreamImplicit
	private List<Issue> issue;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Issue> getIssue() {
		return issue;
	}

	public void setIssue(List<Issue> issue) {
		this.issue = issue;
	}
	
	
	
    
}
