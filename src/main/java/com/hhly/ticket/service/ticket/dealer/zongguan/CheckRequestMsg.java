package com.hhly.ticket.service.ticket.dealer.zongguan;

import java.util.ArrayList;

import com.hhly.ticket.service.ticket.dealer.zongguan.request.CheckBody;
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
public class CheckRequestMsg extends AbstractMsg {   
	
	private CheckBody body;
	
	public CheckRequestMsg(String transcode, String partnerid) {
		super(transcode, partnerid);
		body = new CheckBody();
		body.setQueryTickets(new ArrayList<>());
	}

	/**
	 * @return the body
	 */
	public CheckBody getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(CheckBody body) {
		this.body = body;
	}
    
	
	
}
