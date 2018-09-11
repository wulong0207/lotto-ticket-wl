package com.hhly.ticket.service.ticket.dealer.shenfu;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.shenfu.response.ResponseOrder;
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
@XStreamAlias("message")
public class CathecticResponseMsg extends AbstractXml {
	
	private static XStream XS = XmlUtil.createXStream(CathecticResponseMsg.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	private List<ResponseOrder> orderlist;

	public List<ResponseOrder> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<ResponseOrder> orderlist) {
		this.orderlist = orderlist;
	}

}
