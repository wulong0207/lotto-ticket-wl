package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.jimi.cathectic.JiMiDealer;
/**
 * @desc 云南吉米
 * @author jiangwei
 * @date 2017年12月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class JiMiDealerTest extends DefaultDao{
	
	private JiMiDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("FF40PsJt17LneRxGYbavdQ==");
		bo.setDrawerAccount("700123");
		bo.setSendUrl("http://123.57.147.232:8088/service");
		bo.setSearchMaxTicket(100);
		numberDealer = new JiMiDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
	
	@Test
	public void searchIssue(){
		numberDealer.searchIssue("91");
	}
	
}
