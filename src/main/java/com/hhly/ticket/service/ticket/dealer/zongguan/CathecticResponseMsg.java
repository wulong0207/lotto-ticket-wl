package com.hhly.ticket.service.ticket.dealer.zongguan;

import com.hhly.ticket.service.ticket.dealer.zongguan.response.CathecticBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注相应
 * @author jiangwei
 * @date 2018年3月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class CathecticResponseMsg extends AbstractMsg {
	private static XStream XS = XmlUtil.createXStream(CathecticResponseMsg.class);

	
	private CathecticBody body;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}


	/**
	 * @return the body
	 */
	public CathecticBody getBody() {
		return body;
	}


	/**
	 * @param body the body to set
	 */
	public void setBody(CathecticBody body) {
		this.body = body;
	}

	
}
