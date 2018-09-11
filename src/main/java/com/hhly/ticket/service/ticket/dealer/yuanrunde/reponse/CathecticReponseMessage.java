package com.hhly.ticket.service.ticket.dealer.yuanrunde.reponse;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.yuanrunde.AbstractXml;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class CathecticReponseMessage extends AbstractXml{
	private static XStream  XS;
	
	static{
		XS= XmlUtil.createXStream(CathecticReponseMessage.class);
	}
	
	public List<CathecticReponseOrder> orders;

	public List<CathecticReponseOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<CathecticReponseOrder> orders) {
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
