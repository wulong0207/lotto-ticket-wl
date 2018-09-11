package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.gaode.cathectic.GaoDeDealer;

public class GaoDeDealerTest extends DefaultDao{
	
	private GaoDeDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("vQWhfX1qUho=");
		bo.setDrawerAccount("ycai20170707");
		bo.setSendUrl("http://202.189.3.10:20888/NewChannelService/ChannelService.ashx");
		bo.setSearchMaxTicket(100);
		numberDealer = new GaoDeDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
}
