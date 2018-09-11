package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.shenfu.cathectic.ShenFuDealer;

public class ShenFuDealerTest extends DefaultDao{
	
	private ShenFuDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("MvHmlZG+JNo6cTcjIwv9/kryQarDETSKcWT8bxhy0tuW1olgE0ixpQ==");
		bo.setDrawerAccount("0625ly");
		bo.setSendUrl("http://47.104.83.212/doCpTouzhuAction_caiPiaoInterface.action");
		bo.setSearchMaxTicket(50);
		numberDealer = new ShenFuDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
}
