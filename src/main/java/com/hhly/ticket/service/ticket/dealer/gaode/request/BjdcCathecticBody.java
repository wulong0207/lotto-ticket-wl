package com.hhly.ticket.service.ticket.dealer.gaode.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("body")
public class BjdcCathecticBody extends CathecticBody {
	
    private String issue;

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
     
     
}
