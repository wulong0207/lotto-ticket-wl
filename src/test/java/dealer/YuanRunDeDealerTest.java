package dealer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.ticket.persistence.dao.DefaultDao;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.cathectic.YuanRunDeDealer;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.util.YuanRunDeUtil;

public class YuanRunDeDealerTest extends DefaultDao{
	private YuanRunDeDealer yuanRunDeDealer;
	@Autowired
	private IOrderService orderService;
	
	@Before
	public void before(){
		ChannelBO bo  = new ChannelBO();
		bo.setAccountPassword("B5eMbwViRdmCHisBHXYxYJbWiWATSLGl");
		bo.setDrawerAccount("shyc1536");
		bo.setSendUrl("http://39.106.163.44/submitInterface.action");
		bo.setSearchMaxTicket(50);
		yuanRunDeDealer = new YuanRunDeDealer(bo,orderService);
	}
	
	
	@Test
	public void notifyOutTicket(){
		StringBuffer body = new StringBuffer();
		body.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		body.append("<content>");
		body.append("<head>");
		body.append("<msgId>shyc153610420180810143450</msgId>");
		body.append("<merchantCode>shyc1536</merchantCode>");
		body.append("<commandCode>104</commandCode>");
		body.append("<timestamp>20180810143450</timestamp>");
		body.append("<errorCode>0</errorCode>");
		body.append("</head>");
		body.append("<body>");
		StringBuffer bodys = new StringBuffer();
		bodys.append("<message>");
		bodys.append("<orders>");
		bodys.append("<order>");
		bodys.append("<id>00000000</id>");
		bodys.append("<ticketlist>");
		bodys.append("<ticket plv=\"20160606012(23_34.00)|20160606013(52_33.00)\" printtime=\"2018-08-10 10:55:00\"></ticket>");
//		body.append("<ticket plv=\"20160606013(23_34.00)|20160606014(52_33.00)\" printtime=\"2018-08-10 10:56:00\"></ticket>");
		bodys.append("</ticketlist>");
		bodys.append("<errorCode>0</errorCode> ");
		bodys.append("</order>");
		bodys.append("</orders>");
		bodys.append("</message>");
		body.append(YuanRunDeUtil.desEncrypt(bodys.toString(), "rnpma9tlwxf9as5w"));
		body.append("</body>");
		body.append("<signature>aeb9c8bea4d8391a784249326647c29c</signature>");
		body.append("</content>");
		
		yuanRunDeDealer.notifyOutTicket(body.toString());
	}
}
