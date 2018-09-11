package com.hhly.ticket.service.ticket.dealer.ruilang.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class IssueResponse {
	
	@XStreamAsAttribute
    private String errorCode;
	
	@XStreamAlias("issuequery")
	private List<Issue> issuequery;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<Issue> getIssuequery() {
		return issuequery;
	}

	public void setIssuequery(List<Issue> issuequery) {
		this.issuequery = issuequery;
	}

	
}
