package com.hhly.ticket.service.entity;
/**
 * @desc 送票时间
 * @author jiangwei
 * @date 2017年9月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SendTime {
	// 送票间隔
	private int interval;
	// 出票时间
	private long outTime;
	
	public SendTime(int interval, long outTime) {
		this.interval = interval;
		this.outTime = outTime;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public long getOutTime() {
		return outTime;
	}

	public void setOutTime(long outTime) {
		this.outTime = outTime;
	}

}
