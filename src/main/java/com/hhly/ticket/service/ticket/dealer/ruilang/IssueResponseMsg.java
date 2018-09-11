package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.response.IssueBody;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 彩期请求
 * @author jiangwei
 * @date 2017年5月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class IssueResponseMsg extends AbstractMsg{
	
	private static XStream  XS = XmlUtil.createXStream(IssueResponseMsg.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	private IssueBody body;

	public IssueBody getBody() {
		return body;
	}

	public void setBody(IssueBody body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		String string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg v=\"1.0\" id=\"1705182044290100001\"><ctrl><agentID>800213</agentID><cmd>2017</cmd><timestamp>1495111590815</timestamp><md>1d666ca141935b2944e79aa408c9342d</md></ctrl><body><response errorcode=\"0\"><issuequery><issue lotoid=\"001\" issue=\"2017058\" starttime=\"20170518200000\" endtime=\"20170521194500\" status=\"1\"/></issuequery></response></body></msg>";
		IssueResponseMsg msg = new IssueResponseMsg();
		msg = msg.fromXML(string);
		System.out.println(JsonUtil.object2Json(msg));
	}
}
