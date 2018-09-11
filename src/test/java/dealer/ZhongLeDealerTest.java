package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.zhongle.cathectic.ZhongLeDealer;

public class ZhongLeDealerTest extends DefaultDao{
	
	private ZhongLeDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("PcFII/rRrCkXwol/WoaFSQ==");
		bo.setDrawerAccount("668");
		bo.setSendUrl("http://39.107.108.200:9081/ticketsale");
		bo.setSearchMaxTicket(50);
		numberDealer = new ZhongLeDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
}
