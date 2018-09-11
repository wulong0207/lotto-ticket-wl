package com.hhly.ticket.service.entity;

public class AllowSendTimeBO {
	/**
	 * 星期
	 */
	private String week;
	/**
	 * 允许开始送票时间（秒）
	 */
	private int start;
	/**
	 * 允许送票时间（秒） 
	 */
	private int end;
	
	
	public AllowSendTimeBO(String week, int start, int end) {
		super();
		this.week = week;
		this.start = start;
		this.end = end;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	
}
