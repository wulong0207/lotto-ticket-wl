package dealer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.RuiLangUtil;
import com.hhly.ticket.service.ticket.dealer.ruilang.cathectic.RuilangDealer;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.IssueResponse;
import com.hhly.ticket.util.JsonUtil;
/**
 * @desc 睿朗测试
 * @author jiangwei
 * @date 2017年6月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class RuiLangDealerTest extends DefaultDao {
	
	private RuilangDealer numberDealer;
	@Autowired
	private IOrderService orderService;
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("9YKsJ/uoXHs=");
		bo.setDrawerAccount("800213");
		bo.setSendUrl("http://111.200.217.42:7070/billservice/sltAPI");
		
		/*bo.setAccountPassword("LGpcXsrWhF38brMhnQbpDA==");
		bo.setDrawerAccount("800206");
		bo.setSendUrl("http://apib.wozhongla.com/api/sltAPI");*/
		bo.setSearchMaxTicket(50);
		numberDealer = new RuilangDealer(bo,orderService,0);
		
	}
	@Test
	public void testIssue(){
		String code = RuiLangUtil.getCode(Lottery.JXK3);
	    IssueResponse issueResponse = numberDealer.searchIssue(code,"");
	    System.out.println(JsonUtil.object2Json(issueResponse));
	}
	@Test
	public void checkTicket(){
		List<DealerCheckBO> batchNum = new ArrayList<>();
		batchNum.add(new DealerCheckBO(300, "BB17081012120216600003",1));
		numberDealer.checkTicket(batchNum);
	}
	@Test
	public void balance(){
	    System.out.println(numberDealer.getDealerBalance());
	}
}
