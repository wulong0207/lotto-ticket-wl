package com.hhly.ticket.service.ticket.dealer.hengpeng;

import com.hhly.ticket.service.ticket.dealer.hengpeng.response.Body;
import com.hhly.ticket.service.ticket.dealer.hengpeng.response.Response;
import com.hhly.ticket.util.JsonUtil;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * @desc 响应
 * @author jiangwei
 * @date 2017年8月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("message")
public class ResponseMsg  extends AbstractMsg {
	
	private static XStream XS = XmlUtil.createXStream(ResponseMsg.class);
	public ResponseMsg(){
		
	}
	
	public ResponseMsg(String code,String message){
		Body body = new Body();
		Response response = new Response();
		response.setCode(code);
		response.setMessage(message);
		body.setResponse(response);
		this.body = body;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}
	
	private Body body;
	

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
	
	public static void main(String[] args) {
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"+
				"<message version=\"2.0\" id=\"3899122009101600000231\">"+
				"<header>"+
				"<messengerID>389912</messengerID>"+
				"<timestamp>20091016092435</timestamp>"+
				"<transactionType>501</transactionType>"+
				"<digest>879cba6a680b38bca06922fb72fd76d4</digest>"+
				"</header><body><response code=\"0000\" message=\"成功,系统处理正常\" /> </body></message>";
				System.out.println(str);
				ResponseMsg msg = new ResponseMsg().fromXML(str);
				System.out.println(JsonUtil.object2Json(msg));
	}
}
