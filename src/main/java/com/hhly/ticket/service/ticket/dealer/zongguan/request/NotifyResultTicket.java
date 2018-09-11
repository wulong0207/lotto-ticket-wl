package com.hhly.ticket.service.ticket.dealer.zongguan.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("returnticketresult")
public class NotifyResultTicket {

	@XStreamAsAttribute
	private String lotteryId;

	@XStreamAsAttribute
	private String palmId;

	public NotifyResultTicket(String batchNum, String deawerAccount) {
		this.lotteryId = batchNum;
		this.palmId = deawerAccount;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getPalmId() {
		return palmId;
	}

	public void setPalmId(String palmId) {
		this.palmId = palmId;
	}

}
