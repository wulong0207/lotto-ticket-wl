package com.hhly.ticket.service.ticket.dealer.jimi;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.jimi.cathectic.JiMiDealer;
import com.hhly.ticket.service.ticket.dealer.jimi.response.CheckTicketBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注响应
 * @author jiangwei
 * @date 2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class CheckResponseMsg extends AbstractMsg implements ICheckResponse{
	
	private static XStream XS = XmlUtil.createXStream(CheckResponseMsg.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	private CheckTicketBody body;

	public CheckTicketBody getBody() {
		return body;
	}

	public void setBody(CheckTicketBody body) {
		this.body = body;
	}

	@Override
	public String getCode() {
		return getHeader().getErrorCode();
	}

	@Override
	public String getMessage() {
		return getHeader().getErrorMsg();
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return getBody().getTickets();
	}

	@Override
	public boolean isSuccess() {
		return JiMiDealer.SUCCESS.equals(getHeader().getErrorCode());
	}

	@Override
	public boolean isExist() {
		return !"3003".equals(getHeader().getErrorCode());
	}

	
	
}
