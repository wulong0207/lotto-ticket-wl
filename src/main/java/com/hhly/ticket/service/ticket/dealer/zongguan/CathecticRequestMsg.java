package com.hhly.ticket.service.ticket.dealer.zongguan;

import java.util.ArrayList;

import com.hhly.ticket.service.ticket.dealer.zongguan.request.CathecticBody;
import com.hhly.ticket.service.ticket.dealer.zongguan.request.TicketOrder;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注请求
 * @author jiangwei
 * @date 2018年3月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class CathecticRequestMsg extends AbstractMsg {
	
	private CathecticBody body;
	
	
	public CathecticRequestMsg(String transcode, String partnerid){
		super(transcode, partnerid);
		body = new CathecticBody();
		body.setTicketorder(new TicketOrder());
		body.getTicketorder().setTickets(new ArrayList<>());
	}

	/**
	 * @return the body
	 */
	public CathecticBody getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(CathecticBody body) {
		this.body = body;
	}
}
