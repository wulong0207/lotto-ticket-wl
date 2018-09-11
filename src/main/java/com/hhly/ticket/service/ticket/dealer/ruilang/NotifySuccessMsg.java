package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.response.NotifySuccessBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 返回通知接受消息成功
 * @author jiangwei
 * @date 2017年5月22日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class NotifySuccessMsg extends  AbstractMsg{
	 
	private NotifySuccessBody body = new NotifySuccessBody();

	public NotifySuccessBody getBody() {
		return body;
	}

	public void setBody(NotifySuccessBody body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		NotifySuccessMsg msg= new NotifySuccessMsg();
		System.out.println(msg.getBody().toXml());
		System.out.println(msg.toXml());
	}
	
}
