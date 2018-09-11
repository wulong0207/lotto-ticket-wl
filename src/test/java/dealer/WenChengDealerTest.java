package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.wencheng.cathectic.WenChengDealer;

public class WenChengDealerTest extends DefaultDao{
	
	private WenChengDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("/QUObtTaJrpj0QBoGej+rlu5xgriSPWthqWl6Gmb2YGW1olgE0ixpQ==");
		bo.setDrawerAccount("100001");
		bo.setSendUrl("http://api.kelacp.com");
		bo.setSearchMaxTicket(50);
		numberDealer = new WenChengDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
}
