package com.hhly.ticket.service.ticket.dealer.gaode;

import com.hhly.ticket.service.ticket.dealer.gaode.request.BalanceBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 获取余额
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class BalanceRequestMsg extends AbstractMsg{
	
    private BalanceBody body;

	public BalanceBody getBody() {
		return body;
	}

	public void setBody(BalanceBody body) {
		this.body = body;
	}
	
    public static void main(String[] args) {
    	BalanceRequestMsg msg = new BalanceRequestMsg();
    	msg.setBody(new BalanceBody());
    	Header header = new Header();
    	header.setDigest("23");
    	header.setTimestamp("1");
    	header.setTransactionType("132");
    	header.setUserName("yicai");
    	msg.setHeader(header);
    	System.out.println(msg.toXml());
	}
     
}
