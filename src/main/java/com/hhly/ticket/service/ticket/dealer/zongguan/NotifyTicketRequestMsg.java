package com.hhly.ticket.service.ticket.dealer.zongguan;

import java.util.ArrayList;

import com.hhly.ticket.service.ticket.dealer.zongguan.request.NotifyResultTicketBody;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @desc 检票
 * @author jiangwei
 * @date 2018年3月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class NotifyTicketRequestMsg extends AbstractMsg {

	private NotifyResultTicketBody body;

	public NotifyTicketRequestMsg(String transcode, String partnerid) {
		super(transcode, partnerid);
		body = new NotifyResultTicketBody();
		body.setReturnticketresults(new ArrayList<>());
	}

	public NotifyResultTicketBody getBody() {
		return body;
	}

	public void setBody(NotifyResultTicketBody body) {
		this.body = body;
	}

}
