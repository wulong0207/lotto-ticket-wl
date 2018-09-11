package com.hhly.ticket.service.ticket.dealer.yuanrunde;

import com.hhly.ticket.service.ticket.dealer.yuanrunde.reponse.CathecticReponseHead;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.util.YuanRunDeUtil;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("content")
public class CathecticReponseMsg extends AbstractXml{
	
	private static XStream  XS;
	
	static{
		XS= XmlUtil.createXStream(CathecticReponseMsg.class);
	}
	
	private CathecticReponseHead head;
	
	private String body;
	
	private String signature;

	public CathecticReponseHead getHead() {
		return head;
	}

	public void setHead(CathecticReponseHead head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "CathecticReponseMsg [head=" + head + ", body=" + body + ", signature=" + signature + "]";
	}
	
	
	@Override
	public <T> T fromXML(String xml) {
		   //处理注解
        @SuppressWarnings("unchecked")
        //将XML字符串转为bean对象
        T t = (T)XS.fromXML(xml);
        return t;
	}

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<content>");
		sb.append("<head>");
		sb.append("<msgId>shyc153610420180809145629</msgId>");
		sb.append("<merchantCode>shyc1536</merchantCode>");
		sb.append("<commandCode>104</commandCode>");
		sb.append("<timestamp>20180809145629</timestamp>");
		sb.append("<errorCode>2</errorCode>");
		sb.append("</head>");
//		sb.append("<body>ePDcykR7lIYxvKQ/APui/fncv/CruSrCwpg3KsvOsqxfrKlkaF0TpXCPcZb4xtkn2n/C5VFzkTwdTWghQUEk3vRdVE2E1kf8/QgkySXxiDMBz4Bkw9rhyeN5fK5RMZ6ybFy8IfP13NGgxc5PczClUI7Yjc/iEuxe8nT4kc2oiuAnRz+OX13tFidHP45fXe0WiVQKz5FAtx/Ukw7CFavcJw==</body>");
		sb.append("<body>ePDcykR7lIYxvKQ/APui/fncv/CruSrCwpg3KsvOsqwJzEKWDt8F/5RVFQfPP8i32n/C5VFzkTwdTWghQUEk3mEotfU4/Qto+4rcT3exjfQBz4Bkw9rhyeN5fK5RMZ6ybFy8IfP13NGgxc5PczClUI7Yjc/iEuxe8nT4kc2oiuAnRz+OX13tFidHP45fXe0WiVQKz5FAtx/Ukw7CFavcJw==</body>");
		sb.append("<signature>8be8210666d027d390df69580971f78f</signature>");
		sb.append("</content>");
		String msg = sb.toString();
		CathecticReponseMsg cathecticReponseMsg = new CathecticReponseMsg().fromXML(msg);
		System.out.println(YuanRunDeUtil.desDecrypt(cathecticReponseMsg.getBody(), "rnpma9tlwxf9as5w"));
		System.out.println(cathecticReponseMsg.toString());
	}
}
