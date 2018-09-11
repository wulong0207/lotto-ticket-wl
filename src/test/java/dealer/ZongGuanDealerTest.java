package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.zongguan.cathectic.ZongGuanDealer;

public class ZongGuanDealerTest extends DefaultDao{
	
	private ZongGuanDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("GVHt863fh2WW1olgE0ixpQ==");
		bo.setDrawerAccount("10027");
		bo.setSendUrl("http://120.78.15.80:550/ticketinterface.aspx");
		bo.setSearchMaxTicket(50);
		numberDealer = new ZongGuanDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
}
