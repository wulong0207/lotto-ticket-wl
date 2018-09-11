package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.tengshun.cathectic.TengShunDealer;

public class TengShunDealerTest extends DefaultDao{
	
	private TengShunDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("nA6w/1PrlTI=");
		bo.setDrawerAccount("5005");
		bo.setSendUrl("http://219.234.83.38:9091/test/test.go");
		bo.setSearchMaxTicket(50);
		numberDealer = new TengShunDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
	
	@Test
	public void searchIssue(){
		numberDealer.searchIssue("80");
	}
}
