package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.util.TicketUtil;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * @desc 睿朗请求xml实体类
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractMsg extends AbstractXml{
	
	@XStreamAsAttribute
    private String v = "1.0";
    
	@XStreamAsAttribute
    private String id = TicketUtil.getOnlyNo();
	
    private Ctrl ctrl;

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Ctrl getCtrl() {
		return ctrl;
	}

	public void setCtrl(Ctrl ctrl) {
		this.ctrl = ctrl;
	}

}
