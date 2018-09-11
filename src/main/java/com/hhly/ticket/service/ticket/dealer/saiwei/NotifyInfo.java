package com.hhly.ticket.service.ticket.dealer.saiwei;

public class NotifyInfo {
	private String signature;
	private String tickets;
	private String t;
	private String s;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTickets() {
		return tickets;
	}

	public void setTickets(String tickets) {
		this.tickets = tickets;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return "NotifyInfo [signature=" + signature + ", tickets=" + tickets + ", t=" + t + ", s=" + s + "]";
	}

}
