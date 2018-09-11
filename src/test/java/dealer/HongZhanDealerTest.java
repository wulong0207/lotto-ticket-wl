package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.hongzhan.cathectic.HongZhanDealer;
/**
 * @desc 云南吉米
 * @author jiangwei
 * @date 2017年12月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class HongZhanDealerTest extends DefaultDao{
	
	private HongZhanDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("SbaB/qHYChNXMZ/m55lcAGTcPHhR7Nyjot2TLaH6dM2W1olgE0ixpQ==");
		bo.setDrawerAccount("11000001");
		bo.setSendUrl("http://39.106.174.167:58080/");
		bo.setSearchMaxTicket(100);
		numberDealer = new HongZhanDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(num);
	}
	
	@Test
	public void searchIssue(){
		numberDealer.searchIssue("100","18103");
	}
	
}
