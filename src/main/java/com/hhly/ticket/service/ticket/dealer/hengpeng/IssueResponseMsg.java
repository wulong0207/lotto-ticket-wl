package com.hhly.ticket.service.ticket.dealer.hengpeng;

import com.hhly.ticket.service.ticket.dealer.hengpeng.response.IssueBody;
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
@XStreamAlias("message")
public class IssueResponseMsg extends AbstractMsg {

	private static XStream XS = XmlUtil.createXStream(IssueResponseMsg.class);

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
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"+
		"<message version=\"2.0\" id=\"3899122009101600000231\">"+
		"<header>"+
		"<messengerID>389912</messengerID>"+
		"<timestamp>20091016092435</timestamp>"+
		"<transactionType>501</transactionType>"+
		"<digest>879cba6a680b38bca06922fb72fd76d4</digest>"+
		"</header><body><response code=\"0000\" message=\"成功,系统处理正常\" >"+
		"<issue gameName=\"D11\" number=\"09101616\" startTime=\"2009-10-16 11:51:36\""+
		 " stopTime=\"2009-10-16 12:03:30\" officialStartTime= \"2009-10-16 11:52:00\""+ 
		 " officialStopTime= \"2009-10-16 12:05:00\""+
		 " status=\"5\""+
		 " bonusCode=\"01,02,03,04,05\"/></response> </body></message>";
		System.out.println(str);
		IssueResponseMsg msg = new IssueResponseMsg().fromXML(str);
		System.out.println(JsonUtil.object2Json(msg));
	}

}
