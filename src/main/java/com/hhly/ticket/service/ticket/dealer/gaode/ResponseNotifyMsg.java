package com.hhly.ticket.service.ticket.dealer.gaode;

import com.hhly.ticket.service.ticket.dealer.gaode.response.ResponseNotifyBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 响应通知信息
 * @author jiangwei
 * @date 2017年9月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class ResponseNotifyMsg extends AbstractMsg{
	
	private ResponseNotifyBody body;

	public ResponseNotifyBody getBody() {
		return body;
	}

	public void setBody(ResponseNotifyBody body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		ResponseNotifyMsg msg = new ResponseNotifyMsg();
		Header header = new Header();
    	header.setDigest("23");
    	header.setTimestamp("1");
    	header.setUserName("yicai");
    	msg.setHeader(header);
    	msg.setBody(new ResponseNotifyBody("0"));
    	System.out.println(msg.toXml());
	}
}
