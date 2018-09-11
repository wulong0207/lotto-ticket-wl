package com.hhly.ticket.service.ticket.dealer.tengshun;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.tengshun.response.SearchBody;
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
@XStreamAlias("response")
public class SearchResponseMsg extends AbstractMsg implements ICheckResponse{
	
	private static XStream  XS = XmlUtil.createXStream(SearchResponseMsg.class);	
	
	private SearchBody body;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	@Override
	public String getCode() {
		return getResult().getCode();
	}

	@Override
	public String getMessage() {
		return getResult().getDesc();
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		return body.getRows().getRows();
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
	
	public static void main(String[] args) {
		String str= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><head sid=\"20010\" agent=\"6000\" messageid=\"\" memo=\"\" /><result code=\"0\" desc=\"成功\" /><body><rows><row agent=\"6000\" balance=\"48414\" /></rows></body></response>";
		SearchResponseMsg msg =new SearchResponseMsg().fromXML(str);
		System.out.println(msg.getBody().getRows().getRows().get(0).getBalance());
	}

	
	
}
