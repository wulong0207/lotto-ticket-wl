package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.yaosen.cathectic.YaoSenDealer;
/**
 * @desc 耀森出票商
 * @author jiangwei
 * @date 2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class YaoSenDealerTest extends DefaultDao{
	
	private YaoSenDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("byhEVc9gX2j/03QaxwR5DA==");
		bo.setDrawerAccount("8130000156");
		bo.setSendUrl("http://112.17.64.158:73/service");
		bo.setSearchMaxTicket(100);
		numberDealer = new YaoSenDealer(bo,orderService);
	}
	
	@Test
	public void getDealerBalance(){
		double num= numberDealer.getDealerBalance();
	    System.out.println(String.valueOf(num));
	}
	
	@Test
	public void searchIssue(){
		numberDealer.searchIssue("51");
	}
	
}
