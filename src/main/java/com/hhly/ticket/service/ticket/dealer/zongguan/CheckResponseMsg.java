package com.hhly.ticket.service.ticket.dealer.zongguan;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.zongguan.response.CheckBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注相应
 * @author jiangwei
 * @date 2018年3月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class CheckResponseMsg extends AbstractMsg  implements ICheckResponse{
	
	private static XStream XS = XmlUtil.createXStream(CheckResponseMsg.class);

	
	private CheckBody body;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}


	/**
	 * @return the body
	 */
	public CheckBody getBody() {
		return body;
	}


	/**
	 * @param body the body to set
	 */
	public void setBody(CheckBody body) {
		this.body = body;
	}


	@Override
	public String getCode() {
		return getHead().getTranscode();
	}


	@Override
	public String getMessage() {
		return null;
	}


	@Override
	public List<? extends ICheckTicket> getTicket() {
		return body.getCheckTickets();
	}


	@Override
	public boolean isSuccess() {
		return "103".equals(getCode());
	}


	@Override
	public boolean isExist() {
		return isSuccess();
	}


	

	
}
