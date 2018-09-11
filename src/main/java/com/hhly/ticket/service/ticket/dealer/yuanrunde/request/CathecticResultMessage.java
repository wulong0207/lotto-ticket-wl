package com.hhly.ticket.service.ticket.dealer.yuanrunde.request;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.yuanrunde.AbstractXml;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.CathecticReponseMsg;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class CathecticResultMessage extends AbstractXml{
	private static XStream  XS;
	
	static{
		XS= XmlUtil.createXStream(CathecticReponseMsg.class);
	}
	
	
	private List<CathecticResultOrder> orders;

	public List<CathecticResultOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<CathecticResultOrder> orders) {
		this.orders = orders;
	}
	
	@Override
	public <T> T fromXML(String xml) {
		   //处理注解
        @SuppressWarnings("unchecked")
        //将XML字符串转为bean对象
        T t = (T)XS.fromXML(xml);
        return t;
	}
	
}
