package com.hhly.ticket.service.ticket.dealer.zongguan;

import com.hhly.ticket.service.ticket.dealer.zongguan.request.BalanceBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 获取余额
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class BalanceRequestMsg extends AbstractMsg{
	
	private BalanceBody body;
	
    public BalanceRequestMsg(String transcode,String partnerid) {
		super(transcode, partnerid);
		body = new BalanceBody(partnerid); 
	}

	public BalanceBody getBody() {
		return body;
	}

	public void setBody(BalanceBody body) {
		this.body = body;
	}
	     
}
