package com.hhly.ticket.service.ticket.dealer.tengshun.request;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class RqTickets {
	
	@XStreamAsAttribute
	private String gid;
	@XStreamAsAttribute
	private String pid;
	
	@XStreamImplicit
	private List<RqTicket> tickets;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the tickets
	 */
	public List<RqTicket> getTickets() {
		return tickets;
	}

	/**
	 * @param tickets the tickets to set
	 */
	public void setTickets(List<RqTicket> tickets) {
		this.tickets = tickets;
	}

}
