package com.hhly.ticket.service.ticket.dealer.gaode;

import com.hhly.ticket.service.ticket.dealer.gaode.response.CathecticTicketBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 北京单场投注请求
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class CathecticResponseMsg extends AbstractMsg {
	private static XStream XS = XmlUtil.createXStream(CathecticResponseMsg.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	private CathecticTicketBody body;

	public CathecticTicketBody getBody() {
		return body;
	}

	public void setBody(CathecticTicketBody body) {
		this.body = body;
	}

	public static void main(String[] args) {
		String string = "<?xml version=\"1.0\" encoding=\"utf-8\"?><message version=\"1.0\"><body><responseCode>6</responseCode><requestString><![CDATA[<?xml version=\"1.0\" ?><message version=\"1.0\"><header><userName>Y2ncaipiao</userName><timestamp>20180620164902</timestamp><transactionType>P001</transactionType><digest>d94456952e2edd2ce181654337e8b555</digest></header><body><lotteryId>100</lotteryId><rule>7</rule><tickets><ticket><ticketId>SFG18062016490208502955</ticketId><passMode>3串1</passMode><chips>1</chips><multiple>2</multiple><money>4</money><betContents>4:0/3:3/1:3</betContents></ticket><ticket><ticketId>SFG18062016490208502956</ticketId><passMode>3串1</passMode><chips>1</chips><multiple>3</multiple><money>6</money><betContents>1:3/3:3/4:0</betContents></ticket></tickets><issue>80604</issue></body></message>]]></requestString></body></message>";
		CathecticResponseMsg msg = new CathecticResponseMsg().fromXML(string);
		System.out.println(msg.toXml());
	}

}
