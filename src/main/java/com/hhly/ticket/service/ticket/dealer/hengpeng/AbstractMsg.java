package com.hhly.ticket.service.ticket.dealer.hengpeng;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * @desc 恒朋
 * @author jiangwei
 * @date 2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractMsg extends AbstractXml{
	
	@XStreamAsAttribute
    private String version = "2.0";
    
	@XStreamAsAttribute
    private String id;
	
    private Header header;

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	

}
