package com.hhly.ticket.service.ticket.dealer.zongguan.request;

import java.util.List;

public class NotifyResultTicketBody {
	
    private List<NotifyResultTicket> returnticketresults;

	/**
	 * @return the returnticketresults
	 */
	public List<NotifyResultTicket> getReturnticketresults() {
		return returnticketresults;
	}

	/**
	 * @param returnticketresults the returnticketresults to set
	 */
	public void setReturnticketresults(List<NotifyResultTicket> returnticketresults) {
		this.returnticketresults = returnticketresults;
	}
}
