package com.hhly.ticket.service.ticket.dealer.ruilang;

import com.hhly.ticket.service.ticket.dealer.ruilang.request.CathecticTicket;
import com.hhly.ticket.service.ticket.dealer.ruilang.response.CathecticBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 响应送票信息
 * @desc
 * @author jiangwei
 * @date 2017年5月18日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class CathecticResponseMsg extends AbstractMsg{
	
	private static XStream  XS;
	
	static{
		XS= XmlUtil.createXStream(CathecticResponseMsg.class);
		XS.registerConverter(new CathecticTicket());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}

	private CathecticBody body;
	
	public CathecticBody getBody() {
		return body;
	}

	public void setBody(CathecticBody body) {
		this.body = body;
	}

	public static void main(String[] args) {
		String string = "<?xml version=\"1.0\" ?><msg v=\"1.0\" id=\"1706131819430100001\"><ctrl><agentID>800213</agentID><cmd></cmd><timestamp>1497349183863</timestamp><md>7de734501fec48188c9545e102312734</md></ctrl><body><response errorcode=\"0\"></response></body></msg>";
		CathecticResponseMsg	response =  new CathecticResponseMsg().fromXML(string);
		System.out.println(response.getBody().getResponse().getErrorcode());
	}


	
}
