package com.hhly.ticket.service.ticket.dealer.gaode;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * @desc 高德
 * @author jiangwei
 * @date 2017年8月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractMsg extends AbstractXml{
	
	@XStreamAsAttribute
    private String version = "1.0";

	private Header header;
	
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
