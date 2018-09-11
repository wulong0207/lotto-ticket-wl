package com.hhly.ticket.service.ticket.dealer.zongguan.request;

public class BalanceBody {
   
	private Partneraccount partneraccount;
	

	public BalanceBody(String partnerid) {
		partneraccount = new Partneraccount(partnerid);
	}


	public Partneraccount getPartneraccount() {
		return partneraccount;
	}


	public void setPartneraccount(Partneraccount partneraccount) {
		this.partneraccount = partneraccount;
	}

	
	
	
}
