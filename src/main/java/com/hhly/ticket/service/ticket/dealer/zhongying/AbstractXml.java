package com.hhly.ticket.service.ticket.dealer.zhongying;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.ticket.IXml;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
/**
 * @author jiangwei
 * @date 2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
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
