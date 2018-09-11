package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.saiwei.cathectic.SaiWeiDealer;

public class SaiWeiDealerTest extends DefaultDao{
	
	private SaiWeiDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("tKtI5u/M/OBmggaw3gRoudZopLqLKai1Aqqc04/shwv2/kDEu57sbg==");
		bo.setDrawerAccount("cfd088e3f36110302cf96cc51169bb82");
		bo.setSendUrl("http://cai.pre.wesai.com");
		bo.setSearchMaxTicket(50);
		numberDealer = new SaiWeiDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
		num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
}
