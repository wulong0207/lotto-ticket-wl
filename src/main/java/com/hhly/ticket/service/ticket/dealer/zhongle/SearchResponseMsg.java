package com.hhly.ticket.service.ticket.dealer.zhongle;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.zhongle.response.Match;
import com.hhly.ticket.service.ticket.dealer.zhongle.response.SearchBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 获取余额
 * @author jiangwei
 * @date 2017年9月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("TransResult")
public class SearchResponseMsg extends AbstractMsg implements ICheckResponse{
	
	private static XStream  XS ;	
	
	static{
		XS = XmlUtil.createXStream(SearchResponseMsg.class);
		XS.registerConverter(new Match());
	}
	private SearchBody body;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	@Override
	public String getCode() {
		return getHead().getCode();
	}

	@Override
	public String getMessage() {
		return "";
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return body.getOrders();
	}

	@Override
	public boolean isSuccess() {
		return "0".equals(getCode());
	}

	@Override
	public boolean isExist() {
		return false;
	}
	
	/**
	 * @return the body
	 */
	public SearchBody getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(SearchBody body) {
		this.body = body;
	}	
	
}
