package com.hhly.ticket.service.ticket.dealer.tengshun;

import com.hhly.ticket.util.DateUtil;
import com.hhly.ticket.util.TicketUtil;

public abstract class AbstractMsg extends AbstractXml{
	
	public AbstractMsg(){
		
	}
	
	public AbstractMsg(String sid,String agent){
		head = new Head();
		head.setAgent(agent);
		head.setSid(sid);
		head.setMessageid(TicketUtil.getOnlyNo());
		head.setTimestamp(DateUtil.getNow());
		head.setMemo("");
	}
	
	private Head head;
	
	private Result result;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

}
