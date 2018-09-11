package com.hhly.ticket.service.ticket.dealer.tengshun;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.ticket.IXml;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;


public class AbstractXml implements IXml {

	private static final XStream  XS_TO_XML;
	
	static{
		XS_TO_XML= XmlUtil.createXStream();
	}
	
	@Override
	public String toXml() {
		return XS_TO_XML.toXML(this);
	}

	@Override
	public <T> T fromXML(String xml) {
		throw new ServiceRuntimeException("请实现");
	}
}
