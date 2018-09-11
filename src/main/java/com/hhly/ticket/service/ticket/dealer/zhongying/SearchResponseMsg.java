package com.hhly.ticket.service.ticket.dealer.zhongying;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.zhongying.cathectic.ZhongYingDealer;
import com.hhly.ticket.service.ticket.dealer.zhongying.response.SearchResponseOrder;
import com.hhly.ticket.service.ticket.dealer.zhongying.response.SearchTicket;
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
@XStreamAlias("message")
public class SearchResponseMsg extends AbstractXml implements ICheckResponse{

	private static XStream XS ;

	static{
		XS = XmlUtil.createXStream(SearchResponseMsg.class);
		XS.registerConverter(new SearchTicket());
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	private List<SearchResponseOrder> orderlist;
	
	private String code;
	
	private String message;

	public List<SearchResponseOrder> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<SearchResponseOrder> orderlist) {
		this.orderlist = orderlist;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return orderlist;
	}

	@Override
	public boolean isSuccess() {
		return ZhongYingDealer.SUCCESS.equals(code);
	}

	@Override
	public boolean isExist() {
		return "5".equals(code);
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
