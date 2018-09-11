package dealer;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.hengpeng.cathectic.HengPengDealer;
import com.hhly.ticket.service.ticket.dealer.hengpeng.response.Issue;
import com.hhly.ticket.util.JsonUtil;

public class HengPengDealerTest extends DefaultDao{
	
	private HengPengDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("H5nkh+OcZRY=");
		bo.setDrawerAccount("389927");
		bo.setSendUrl("http://116.228.224.157:58083/AgentPortalnormal/common/HttpTransRequestServlet");
		bo.setSearchMaxTicket(5);
		numberDealer = new HengPengDealer(bo,orderService);
	}
	
	@Test
	public void testIssue(){
		List<Issue> issues = numberDealer.searchIssue(Lottery.SSQ,"");
	    System.out.println(JsonUtil.objectList2Json(issues));
	}
}
