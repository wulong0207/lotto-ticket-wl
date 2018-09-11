package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.zhongying.cathectic.ZhongYingDealer;

public class ZhongYingDealerTest extends DefaultDao{
	
	private ZhongYingDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("FPkBPUhXPZIswKlgBFbxZw==");
		bo.setDrawerAccount("80019");
		bo.setSendUrl("http://47.93.87.34/lottery/b2b/bet");
		bo.setSearchMaxTicket(50);
		numberDealer = new ZhongYingDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
}
