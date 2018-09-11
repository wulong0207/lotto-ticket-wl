package com.hhly.ticket.service.ticket.dealer.ruilang.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @desc 检票
 * @author jiangwei
 * @date 2017年5月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("order")
public class CheckOrder {
	
	@XStreamAsAttribute
    private String merchantid;
	
	@XStreamAsAttribute
    private String orderno;
    
	public CheckOrder(String merchantid, String orderno) {
		super();
		this.merchantid = merchantid;
		this.orderno = orderno;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
    
}
