package com.hhly.ticket.service.ticket.dealer.hengpeng.request;

import com.hhly.ticket.service.ticket.dealer.hengpeng.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 彩期查询
 * @author jiangwei
 * @date 2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("body")
public class IssueBody extends AbstractXml{
	
	private IssueQuery issueQuery;

	public IssueQuery getIssueQuery() {
		return issueQuery;
	}

	public void setIssueQuery(IssueQuery issueQuery) {
		this.issueQuery = issueQuery;
	}
	
	
	
}
