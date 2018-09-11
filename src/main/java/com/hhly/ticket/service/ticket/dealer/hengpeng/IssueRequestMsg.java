package com.hhly.ticket.service.ticket.dealer.hengpeng;

import com.hhly.ticket.service.ticket.dealer.hengpeng.request.IssueBody;
import com.hhly.ticket.service.ticket.dealer.hengpeng.request.IssueQuery;
import com.hhly.ticket.util.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 彩期请求
 * @author jiangwei
 * @date 2017年5月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class IssueRequestMsg  extends AbstractMsg{
    
	private IssueBody body;
  
	public IssueRequestMsg(){
		
	}
	
	public IssueRequestMsg(String gameName,String number){
		IssueQuery issueQuery = new IssueQuery();
		Issue issue = new  Issue(gameName, number);
		body = new IssueBody();
		body.setIssueQuery(issueQuery);
		issueQuery.setIssue(issue);
	}
	
	public IssueBody getBody() {
		return body;
	}

	public void setBody(IssueBody body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		IssueRequestMsg  msg = new IssueRequestMsg("D1","51105");
		msg.setId("1");
		Header header = new Header();
		header.setMessengerID("12346");
		header.setTransactionType("101");
		header.setDigest("1234");
		header.setTimestamp(DateUtil.getNow(DateUtil.DATE_FORMAT_NUM));
		msg.setHeader(header);
		System.out.println(msg.toXml());
	}
}
