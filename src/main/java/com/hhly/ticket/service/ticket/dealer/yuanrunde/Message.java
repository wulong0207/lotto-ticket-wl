package com.hhly.ticket.service.ticket.dealer.yuanrunde;

import java.util.List;

import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class Message extends AbstractXml{
	private static XStream  XS;
	
	static{
		XS= XmlUtil.createXStream(Message.class);
	}
	
	public List<Order> orders;

	public List<Order> records;

	public List<Order> getRecords() {
		return records;
	}

	public void setRecords(List<Order> records) {
		this.records = records;
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
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
