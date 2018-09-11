package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.juxiang.cathectic.JuXiangDealer;

public class JuXiangDealerTest extends DefaultDao{
	
	private JuXiangDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("HRGhG07imVrvipI77isD2uxN+bCS9pA0ltaJYBNIsaU=");
		bo.setDrawerAccount("450028");
		bo.setSendUrl("http://dev.joywifi.net:9000/partner");
		bo.setSearchMaxTicket(50);
		numberDealer = new JuXiangDealer(bo,orderService);
	}
	
	@Test
	public void testIssue(){
		String String = numberDealer.searchIssue(Lottery.GX11X5,"");
	    System.out.println(String);
	}
	
	@Test
	public void getBalance(){
		System.out.println(numberDealer.getDealerBalance());
	}
}
